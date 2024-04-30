package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.DepartmentRequestDTO;
import org.ms.requestmanager.dto.DepartmentResponseDTO;
import org.ms.requestmanager.entities.Department;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.DepartmentMapper;
import org.ms.requestmanager.repositories.DepartmentRepository;
import org.ms.requestmanager.repositories.LevelRepository;
import org.ms.requestmanager.repositories.PersonalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final LevelRepository levelRepository;
    private final PersonalRepository personalRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, LevelRepository levelRepository, PersonalRepository personalRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.levelRepository = levelRepository;
        this.personalRepository = personalRepository;
    }

    @Override
    public DepartmentResponseDTO saveDepartment(DepartmentRequestDTO departmentRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(departmentRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le name reçu n'existe pas déjà
        Department aC = departmentRepository.findByName(departmentRequestDTO.getName());
        if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        //Faire le mapping et enregistrer
        Department department = departmentMapper.departmentRequestDTOToDepartment(departmentRequestDTO);
        department.setCreatedAt(Instant.now());
        return departmentMapper.departmentToDepartmentResponseDTO(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponseDTO getDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found!");
        return departmentMapper.departmentToDepartmentResponseDTO(department);
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
        //Faire la sauvegarde
        Department department = departmentMapper.departmentRequestDTOToDepartment(departmentRequestDTO);
        department.setId(departmentId);
        department.setCreatedAt(departmentLast.getCreatedAt());
        department.setUpdatedAt(Instant.now());
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
            departmentRepository.deleteById(departmentId);
        }
        else throw new RessourceNotFoundException("Department Not Exist!");
    }
}
