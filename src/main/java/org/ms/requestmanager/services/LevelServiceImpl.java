package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.LevelRequestDTO;
import org.ms.requestmanager.dto.LevelResponseDTO;
import org.ms.requestmanager.entities.Department;
import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.LevelMapper;
import org.ms.requestmanager.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;
    private final LevelMapper levelMapper;
    private final DepartmentRepository departmentRepository;
    private final PersonalRepository personalRepository;
    private final UeRepository ueRepository;
    private final StudentRepository studentRepository;

    public LevelServiceImpl(LevelRepository levelRepository, LevelMapper levelMapper, DepartmentRepository departmentRepository, PersonalRepository personalRepository, UeRepository ueRepository, StudentRepository studentRepository) {
        this.levelRepository = levelRepository;
        this.levelMapper = levelMapper;
        this.departmentRepository = departmentRepository;
        this.personalRepository = personalRepository;
        this.ueRepository = ueRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public LevelResponseDTO saveLevel(LevelRequestDTO levelRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(levelRequestDTO.getName().equals("") || levelRequestDTO.getDepartmentId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le Department Id passé en paramètre existe vraiment
        Department department = departmentRepository.findById(levelRequestDTO.getDepartmentId()).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found for this departmentId!");
        //Define the president of jury
        Personal personal = null;
        if(levelRequestDTO.getPersonalId()!=null)
            personal = personalRepository.findById(levelRequestDTO.getPersonalId()).orElse(null);
        //Faire le mapping et enregistrer
        Level level = levelMapper.levelRequestDTOToLevel(levelRequestDTO);
        level.setCreatedAt(Instant.now());
        level.setDepartment(department);
        level.setPersonal(personal);
        return levelMapper.levelToLevelResponseDTO(levelRepository.save(level));
    }

    @Override
    public LevelResponseDTO getLevel(Long levelId) {
        Level level = levelRepository.findById(levelId).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found!");
        return levelMapper.levelToLevelResponseDTO(level);
    }

    @Override
    public List<LevelResponseDTO> getAllLevels() {
        List<Level> levels = levelRepository.findAll();
        return levels.stream()
                .map(levelMapper::levelToLevelResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LevelResponseDTO> getAllLevelsByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Level Not Found for this departmentId!");
        List<Level> levels = levelRepository.findByDepartment(department);
        return levels.stream()
                .map(levelMapper::levelToLevelResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LevelResponseDTO> getAllLevelsByPersonal(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("personal Not Found for this personalId!");
        List<Level> levels = levelRepository.findByPersonal(personal);
        return levels.stream()
                .map(levelMapper::levelToLevelResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LevelResponseDTO updateLevel(Long levelId, LevelRequestDTO levelRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(levelRequestDTO.getName().equals("") || levelRequestDTO.getDepartmentId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le Department Id passé en paramètre existe vraiment
        Department department = departmentRepository.findById(levelRequestDTO.getDepartmentId()).orElse(null);
        if(department == null) throw new RessourceNotFoundException("Department Not Found for this departmentId!");
        //Define the president of jury
        Personal personal = null;
        if(levelRequestDTO.getPersonalId()!=null)
            personal = personalRepository.findById(levelRequestDTO.getPersonalId()).orElse(null);
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Level levelLast = levelRepository.findById(levelId).orElse(null);
        if(levelLast == null) throw new RessourceNotFoundException("Level not exist!");
        //Faire la sauvegarde
        Level level = levelMapper.levelRequestDTOToLevel(levelRequestDTO);
        level.setId(levelId);
        level.setDepartment(department);
        level.setPersonal(personal);
        level.setCreatedAt(levelLast.getCreatedAt());
        level.setUpdatedAt(Instant.now());
        return levelMapper.levelToLevelResponseDTO(levelRepository.save(level));
    }

    @Override
    public void deleteLevel(Long levelId) {
        Level level = levelRepository.findById(levelId).orElse(null);
        if(level != null){
            if(!ueRepository.findByLevel(level).isEmpty())
                throw new RessourceNotFoundException("Error : Ue Exist for this Level Id!");
            if(!studentRepository.findByLevel(level).isEmpty())
                throw new RessourceNotFoundException("Error : Student Exist for this Level Id!");
            levelRepository.deleteById(levelId);
        }
        else throw new RessourceNotFoundException("Level Not Exist!");
    }
}
