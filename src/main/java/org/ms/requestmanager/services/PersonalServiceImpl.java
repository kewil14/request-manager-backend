package org.ms.requestmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ms.requestmanager.dto.PersonalRequestDTO;
import org.ms.requestmanager.dto.PersonalResponseDTO;
import org.ms.requestmanager.entities.*;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.PersonalMapper;
import org.ms.requestmanager.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonalServiceImpl implements PersonalService {
    private final PersonalRepository personalRepository;
    private final PersonalMapper personalMapper;
    private final TypePersonalRepository typePersonalRepository;
    private final DepartmentRepository departmentRepository;
    private final UeRepository ueRepository;
    private final RequestRepository requestRepository;
    private final CommentRequestRepository commentRequestRepository;
    private final TransfertRequestRepository transfertRequestRepository;
    private final AppUserRepository appUserRepository;
    private final FileService fileService;

    public PersonalServiceImpl(PersonalRepository personalRepository, PersonalMapper personalMapper, TypePersonalRepository typePersonalRepository, DepartmentRepository departmentRepository, UeRepository ueRepository, RequestRepository requestRepository, CommentRequestRepository commentRequestRepository, TransfertRequestRepository transfertRequestRepository, AppUserRepository appUserRepository, FileService fileService) {
        this.personalRepository = personalRepository;
        this.personalMapper = personalMapper;
        this.typePersonalRepository = typePersonalRepository;
        this.departmentRepository = departmentRepository;
        this.ueRepository = ueRepository;
        this.requestRepository = requestRepository;
        this.commentRequestRepository = commentRequestRepository;
        this.transfertRequestRepository = transfertRequestRepository;
        this.appUserRepository = appUserRepository;
        this.fileService = fileService;
    }

    @Override
    public PersonalResponseDTO savePersonal(MultipartFile file, String personalIn) throws JsonProcessingException {
        PersonalRequestDTO personalRequestDTO = new ObjectMapper().readValue(personalIn, PersonalRequestDTO.class);
        //Vérifier que tous les paramètres ont été reçus
        if(personalRequestDTO.getGrade().equals("") || personalRequestDTO.getFirstname().equals("")
                || personalRequestDTO.getTypePersonalId() == null || personalRequestDTO.getDepartmentId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le TypePersonal Id passé en paramètre existe vraiment
        TypePersonal typePersonal = typePersonalRepository.findById(personalRequestDTO.getTypePersonalId()).orElse(null);
        if(typePersonal == null) throw new RessourceNotFoundException("TypePersonal Not Found for this typePersonalId!");
        //Vérifier si le Department Id passé en paramètre existe vraiment
        Department department = departmentRepository.findById(personalRequestDTO.getDepartmentId()).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found for this departmentId!");
        //check User
        AppUser appUser = null;
        if(personalRequestDTO.getUserId()!=null){
            appUser = appUserRepository.findById(personalRequestDTO.getUserId()).orElse(null);
            if(appUser == null) throw new RessourceNotFoundException("User Not Found for this UserId!");
            //Vérifier si l'user reçu n'est pas déjà associé
            Personal p = personalRepository.findByAppUser(appUser);
            if(p!=null) throw new RessourceNotFoundException("User has already linked to Personal");
        }
        //Faire le mapping et enregistrer
        Personal personal = personalMapper.personalRequestDTOToPersonal(personalRequestDTO);
        personal.setCreatedAt(Instant.now());
        personal.setTypePersonal(typePersonal);
        personal.setDepartment(department);
        personal.setAppUser(appUser);
        //Sauvegarde du fichier
        String extensionFile = fileService.getFileExtension(file.getOriginalFilename());
        if(!(extensionFile.equals("jpg") || extensionFile.equals("jpeg") || extensionFile.equals("png") || extensionFile.equals("svg")))
            throw new RessourceNotFoundException("Unsupported file extension ! Good Extension : jpg, jpeg, png & svg.");
        String fileName = "picture_"+
                personal.getFirstname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                personal.getLastname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                new Date().getTime()+"."+extensionFile;
        fileService.storeFile(file, fileName, "files/pictures");
        personal.setPicture(fileName);
        return personalMapper.personalToPersonalResponseDTO(personalRepository.save(personal));
    }

    @Override
    public PersonalResponseDTO getPersonal(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found!");
        return personalMapper.personalToPersonalResponseDTO(personal);
    }

    @Override
    public ResponseEntity<byte[]> getPersonalPicture(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("personal Not Found!");
        if(personal.getPicture() == null) throw new RessourceNotFoundException("No picture for this personal!");
        return fileService.getFile("files/pictures", personal.getPicture());
    }

    @Override
    public Path getPersonalPathPicture(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("personal Not Found!");
        if(personal.getPicture() == null) throw new RessourceNotFoundException("No picture for this personal!");
        return fileService.getPathFile("files/pictures", personal.getPicture());
    }

    @Override
    public PersonalResponseDTO getPersonalByUser(String userId) {
        AppUser appUser = appUserRepository.findById(userId).orElse(null);
        Personal personal = personalRepository.findByAppUser(appUser);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found!");
        return personalMapper.personalToPersonalResponseDTO(personal);
    }

    @Override
    public List<PersonalResponseDTO> getAllPersonals() {
        List<Personal> personals = personalRepository.findAll();
        return personals.stream()
                .map(personalMapper::personalToPersonalResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonalResponseDTO> getAllPersonalsByTypePersonal(Long typePersonalId) {
        TypePersonal typePersonal = typePersonalRepository.findById(typePersonalId).orElse(null);
        if(typePersonal == null) throw new RessourceNotFoundException("TypePersonal Not Found for this typePersonalId!");
        List<Personal> personals = personalRepository.findByTypePersonal(typePersonal);
        return personals.stream()
                .map(personalMapper::personalToPersonalResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonalResponseDTO> getAllPersonalsByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found for this departmentId!");
        List<Personal> personals = personalRepository.findByDepartment(department);
        return personals.stream()
                .map(personalMapper::personalToPersonalResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonalResponseDTO updatePersonal(Long personalId, PersonalRequestDTO personalRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(personalRequestDTO.getGrade().equals("") || personalRequestDTO.getFirstname().equals("")
                || personalRequestDTO.getTypePersonalId() == null || personalRequestDTO.getDepartmentId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le TypePersonal Id passé en paramètre existe vraiment
        TypePersonal typePersonal = typePersonalRepository.findById(personalRequestDTO.getTypePersonalId()).orElse(null);
        if(typePersonal == null) throw new RessourceNotFoundException("TypePersonal Not Found for this typePersonalId!");
        //Vérifier si le Department Id passé en paramètre existe vraiment
        Department department = departmentRepository.findById(personalRequestDTO.getDepartmentId()).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found for this departmentId!");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Personal personalLast = personalRepository.findById(personalId).orElse(null);
        if(personalLast == null) throw new RessourceNotFoundException("Personal not exist!");
        //check User
        AppUser appUser = null;
        if(personalRequestDTO.getUserId()!=null){
            appUser = appUserRepository.findById(personalRequestDTO.getUserId()).orElse(null);
            if(appUser == null) throw new RessourceNotFoundException("User Not Found for this UserId!");
            //Vérifier si l'user reçu n'est pas déjà associé s'il a été modifié
            if(!(appUser == personalLast.getAppUser())){
                Personal p = personalRepository.findByAppUser(appUser);
                if(p!=null) throw new RessourceNotFoundException("User has already linked to Personal");
            }
        }
        //Faire la sauvegarde
        Personal personal = personalMapper.personalRequestDTOToPersonal(personalRequestDTO);
        personal.setId(personalId);
        personal.setTypePersonal(typePersonal);
        personal.setDepartment(department);
        personal.setCreatedAt(personalLast.getCreatedAt());
        personal.setUpdatedAt(Instant.now());
        personal.setAppUser(appUser);
        return personalMapper.personalToPersonalResponseDTO(personalRepository.save(personal));
    }

    @Override
    public PersonalResponseDTO updatePersonalPicture(Long personalId, MultipartFile file) {
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal not exist!");
        //Sauvegarde du fichier
        String extensionFile = fileService.getFileExtension(file.getOriginalFilename());
        if(!(extensionFile.equals("jpg") || extensionFile.equals("jpeg") || extensionFile.equals("png") || extensionFile.equals("svg")))
            throw new RessourceNotFoundException("Unsupported file extension ! Good Extension : jpg, jpeg, png & svg.");
        String fileName = "picture_"+
                personal.getFirstname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                personal.getLastname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                new Date().getTime()+"."+extensionFile;
        if(personal.getPicture() != null) fileService.deleteFile(personal.getPicture(), "files/pictures");
        fileService.storeFile(file, fileName, "files/pictures");
        personal.setPicture(fileName);
        personal.setUpdatedAt(Instant.now());
        return personalMapper.personalToPersonalResponseDTO(personalRepository.save(personal));
    }

    @Override
    public void deletePersonal(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal != null){
            if(!ueRepository.findByPersonal(personal).isEmpty())
                throw new RessourceNotFoundException("Error : Ue Exist for this Personal Id!");
            if(!requestRepository.findByPersonal(personal).isEmpty())
                throw new RessourceNotFoundException("Error : Request Exist for this Personal Id!");
            if(!commentRequestRepository.findByPersonal(personal).isEmpty())
                throw new RessourceNotFoundException("Error : Comment of Request Exist for this Personal Id!");
            if(!transfertRequestRepository.findByPersonal(personal).isEmpty())
                throw new RessourceNotFoundException("Error : Transfert of Request Exist for this Personal Id!");
            personalRepository.deleteById(personalId);
        }
        else throw new RessourceNotFoundException("Personal Not Exist!");
    }
}
