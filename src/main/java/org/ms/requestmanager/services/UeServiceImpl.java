package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.UeRequestDTO;
import org.ms.requestmanager.dto.UeResponseDTO;
import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Ue;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.UeMapper;
import org.ms.requestmanager.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UeServiceImpl implements UeService {
    private final UeRepository ueRepository;
    private final UeMapper ueMapper;
    private final LevelRepository levelRepository;
    private final PersonalRepository personalRepository;
    private final RessourceRepository ressourceRepository;
    private final RequestRepository requestRepository;

    public UeServiceImpl(UeRepository ueRepository, UeMapper ueMapper, LevelRepository levelRepository, PersonalRepository personalRepository, RessourceRepository ressourceRepository, RequestRepository requestRepository) {
        this.ueRepository = ueRepository;
        this.ueMapper = ueMapper;
        this.levelRepository = levelRepository;
        this.personalRepository = personalRepository;
        this.ressourceRepository = ressourceRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public UeResponseDTO saveUe(UeRequestDTO ueRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(ueRequestDTO.getCode().equals("") || ueRequestDTO.getTitle().equals("") || ueRequestDTO.getSemester() == null
                || ueRequestDTO.getLevelId() == null || ueRequestDTO.getPersonalId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier le semestre
        if(!(ueRequestDTO.getSemester()==1L || ueRequestDTO.getSemester()==2L))
            throw new RessourceNotFoundException("Semester value must be 1 or 2.");
        //Vérifier si le code reçu n'existe pas déjà
        Ue aC = ueRepository.findByCode(ueRequestDTO.getCode());
        if(aC!=null) throw new RessourceNotFoundException("Code already exist");
        //Vérifier si le Level Id passé en paramètre existe vraiment
        Level level = levelRepository.findById(ueRequestDTO.getLevelId()).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found for this levelId!");
        //Vérifier si le Personal Id passé en paramètre existe vraiment
        Personal personal = personalRepository.findById(ueRequestDTO.getPersonalId()).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
        //Faire le mapping et enregistrer
        Ue ue = ueMapper.ueRequestDTOToUe(ueRequestDTO);
        ue.setCreatedAt(Instant.now());
        ue.setLevel(level);
        ue.setPersonal(personal);
        return ueMapper.ueToUeResponseDTO(ueRepository.save(ue));
    }

    @Override
    public UeResponseDTO getUe(Long ueId) {
        Ue ue = ueRepository.findById(ueId).orElse(null);
        if(ue == null) throw new RessourceNotFoundException("Ue Not Found!");
        return ueMapper.ueToUeResponseDTO(ue);
    }

    @Override
    public List<UeResponseDTO> getAllUes() {
        List<Ue> ues = ueRepository.findAll();
        return ues.stream()
                .map(ueMapper::ueToUeResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UeResponseDTO> getAllUesByLevel(Long levelId) {
        Level level = levelRepository.findById(levelId).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found for this levelId!");
        List<Ue> ues = ueRepository.findByLevel(level);
        return ues.stream()
                .map(ueMapper::ueToUeResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UeResponseDTO> getAllUesByLevelAndSemester(Long levelId, Long semester) {
        Level level = levelRepository.findById(levelId).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found for this levelId!");
        //Vérifier le semestre
        if(!(semester==1L || semester==2L))
            throw new RessourceNotFoundException("Semester value must be 1 or 2.");
        List<Ue> ues = ueRepository.findByLevelAndSemester(level,semester);
        return ues.stream()
                .map(ueMapper::ueToUeResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UeResponseDTO> getAllUesByPersonal(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
        List<Ue> ues = ueRepository.findByPersonal(personal);
        return ues.stream()
                .map(ueMapper::ueToUeResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UeResponseDTO updateUe(Long ueId, UeRequestDTO ueRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(ueRequestDTO.getCode().equals("") || ueRequestDTO.getTitle().equals("") || ueRequestDTO.getSemester() ==null
                || ueRequestDTO.getLevelId() == null || ueRequestDTO.getPersonalId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier le semestre
        if(!(ueRequestDTO.getSemester()==1L || ueRequestDTO.getSemester()==2L))
            throw new RessourceNotFoundException("Semester value must be 1 or 2.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Ue ueLast = ueRepository.findById(ueId).orElse(null);
        if(ueLast == null) throw new RessourceNotFoundException("Ue not exist!");
        //Vérifier si le Level Id passé en paramètre existe vraiment
        Level level = levelRepository.findById(ueRequestDTO.getLevelId()).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found for this levelId!");
        //Vérifier si le Personal Id passé en paramètre existe vraiment
        Personal personal = personalRepository.findById(ueRequestDTO.getPersonalId()).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
        //S'assurer que le code n'existe pas s'il a été modifié
        if(!ueRequestDTO.getCode().equals(ueLast.getCode())){
            Ue aC = ueRepository.findByCode(ueRequestDTO.getCode());
            if(aC!=null) throw new RessourceNotFoundException("Code already exist");
        }
        //Faire la sauvegarde
        Ue ue = ueMapper.ueRequestDTOToUe(ueRequestDTO);
        ue.setId(ueId);
        ue.setLevel(level);
        ue.setPersonal(personal);
        ue.setCreatedAt(ueLast.getCreatedAt());
        ue.setUpdatedAt(Instant.now());
        return ueMapper.ueToUeResponseDTO(ueRepository.save(ue));
    }

    @Override
    public void deleteUe(Long ueId) {
        Ue ue = ueRepository.findById(ueId).orElse(null);
        if(ue != null){
            if(!requestRepository.findByUe(ue).isEmpty())
                throw new RessourceNotFoundException("Error : Requests Exist for this Ue Id!");
            if(!ressourceRepository.findByUe(ue).isEmpty())
                throw new RessourceNotFoundException("Error : Ressources Exist for this Ue Id!");
            ueRepository.deleteById(ueId);
        }
        else throw new RessourceNotFoundException("Ue Not Exist!");
    }
}
