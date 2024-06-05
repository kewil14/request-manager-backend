package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.DepartmentRequestDTO;
import org.ms.requestmanager.dto.DepartmentResponseDTO;
import org.ms.requestmanager.entities.Department;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.DepartmentMapper;
import org.ms.requestmanager.repositories.DepartmentRepository;
import org.ms.requestmanager.repositories.LevelRepository;
import org.ms.requestmanager.repositories.PersonalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final LevelRepository levelRepository;
    private final PersonalRepository personalRepository;
    private final FileService fileService;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, LevelRepository levelRepository, PersonalRepository personalRepository, FileService fileService) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.levelRepository = levelRepository;
        this.personalRepository = personalRepository;
        this.fileService = fileService;
    }

    @Override
    public DepartmentResponseDTO saveDepartment(DepartmentRequestDTO departmentRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(departmentRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le name reçu n'existe pas déjà
        Department aC = departmentRepository.findByName(departmentRequestDTO.getName());
        if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        //Define the chief
        Personal personal = null;
        if(departmentRequestDTO.getPersonalId()!=null)
            personal = personalRepository.findById(departmentRequestDTO.getPersonalId()).orElse(null);
        //Faire le mapping et enregistrer
        Department department = departmentMapper.departmentRequestDTOToDepartment(departmentRequestDTO);
        department.setCreatedAt(Instant.now());
        department.setPersonal(personal);
        return departmentMapper.departmentToDepartmentResponseDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponseDTO getDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found!");
        return departmentMapper.departmentToDepartmentResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO defineTimeTableDepartment(MultipartFile file, Long departmentId, int semester) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found!");
        if(!(semester == 1 || semester == 2)) throw new RessourceNotFoundException("Semester must be 1 or 2.");
        String extensionFile = fileService.getFileExtension(file.getOriginalFilename());
        if(!(extensionFile.equals("pdf") || extensionFile.equals("doc") || extensionFile.equals("docx") ||
                extensionFile.equals("ppt") || extensionFile.equals("pptx") || extensionFile.equals("zip")))
            throw new RessourceNotFoundException("Unsupported file extension ! Good Extension : PDF.");
        String namedept = department.getName().replace(":", "_").replace(".", "_").replace(" ", "_");
        String fileName = namedept+"_Semester_"+semester+"_"+new Date().getTime()+"."+extensionFile;
        if(semester == 1){
            if(department.getLinkTimetableS1() != null) fileService.deleteFile(department.getLinkTimetableS1(),"files/timetable/"+namedept);
            department.setLinkTimetableS1(fileName);
        }
        else {
            if(department.getLinkTimetableS2() != null) fileService.deleteFile(department.getLinkTimetableS2(),"files/timetable/"+namedept);
            department.setLinkTimetableS2(fileName);
        }
        fileService.storeFile(file, fileName, "files/timetable/"+namedept);
        department.setUpdatedAt(Instant.now());
        return departmentMapper.departmentToDepartmentResponseDTO(department);
    }

    @Override
    public ResponseEntity<byte[]> getTimetableDepartment(Long departmentId, int semester) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found!");
        if(!(semester == 1 || semester == 2)) throw new RessourceNotFoundException("Semester must be 1 or 2.");
        String namedept = department.getName().replace(":", "_").replace(".", "_").replace(" ", "_");
        if(semester == 1) return fileService.getFile("files/timetable/"+namedept, department.getLinkTimetableS1());
        else return fileService.getFile("files/timetable/"+namedept, department.getLinkTimetableS2());
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::departmentToDepartmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponseDTO updateDepartment(Long departmentId, DepartmentRequestDTO departmentRequestDTO) {
        //Vérifier que tous les paramètres sont bien reçus
        if(departmentRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Department departmentLast = departmentRepository.findById(departmentId).orElse(null);
        if(departmentLast == null) throw new RessourceNotFoundException("Department not exist!");
        //S'assurer que le name n'existe pas s'il a été modifié
        if(!departmentRequestDTO.getName().equals(departmentLast.getName())){
            Department aC = departmentRepository.findByName(departmentRequestDTO.getName());
            if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        }
        //Define the chief
        Personal personal = null;
        if(departmentRequestDTO.getPersonalId()!=null)
            personal = personalRepository.findById(departmentRequestDTO.getPersonalId()).orElse(null);
        //Faire la sauvegarde
        Department department = departmentMapper.departmentRequestDTOToDepartment(departmentRequestDTO);
        department.setId(departmentId);
        department.setCreatedAt(departmentLast.getCreatedAt());
        department.setUpdatedAt(Instant.now());
        department.setPersonal(personal);
        return departmentMapper.departmentToDepartmentResponseDTO(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department != null){
            if(!levelRepository.findByDepartment(department).isEmpty())
                throw new RessourceNotFoundException("Error : Level Exist for this Department Id!");
            if(!personalRepository.findByDepartment(department).isEmpty())
                throw new RessourceNotFoundException("Error : Personal Exist for this Department Id!");
            String namedept = department.getName().
                    replace(":", "_").
                    replace(".", "_").
                    replace(" ", "_");
            if(department.getLinkTimetableS1() != null)
                fileService.deleteFile(department.getLinkTimetableS1(),"files/timetable/"+namedept);
            if(department.getLinkTimetableS2() != null)
                fileService.deleteFile(department.getLinkTimetableS2(),"files/timetable/"+namedept);
            departmentRepository.deleteById(departmentId);
        }
        else throw new RessourceNotFoundException("Department Not Exist!");
    }
}
