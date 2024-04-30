package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.StudentRequestDTO;
import org.ms.requestmanager.dto.StudentResponseDTO;
import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Student;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.StudentMapper;
import org.ms.requestmanager.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final LevelRepository levelRepository;
    private final RequestRepository requestRepository;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, LevelRepository levelRepository, RequestRepository requestRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.levelRepository = levelRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(studentRequestDTO.getMatricule().equals("") || studentRequestDTO.getFirstname().equals("")
                || studentRequestDTO.getLevelId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le matricule reçu n'existe pas déjà
        Student aC = studentRepository.findByMatricule(studentRequestDTO.getMatricule());
        if(aC!=null) throw new RessourceNotFoundException("Matricule already exist");
        //Vérifier si le Level Id passé en paramètre existe vraiment
        Level level = levelRepository.findById(studentRequestDTO.getLevelId()).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found for this levelId!");
        //Faire le mapping et enregistrer
        Student student = studentMapper.studentRequestDTOToStudent(studentRequestDTO);
        student.setCreatedAt(Instant.now());
        return studentMapper.studentToStudentResponseDTO(studentRepository.save(student));
    }

    @Override
    public StudentResponseDTO getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student Not Found!");
        return studentMapper.studentToStudentResponseDTO(student);
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::studentToStudentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponseDTO> getAllStudentsByLevel(Long levelId) {
        Level level = levelRepository.findById(levelId).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found for this levelId!");
        List<Student> students = studentRepository.findByLevel(level);
        return students.stream()
                .map(studentMapper::studentToStudentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO updateStudent(Long studentId, StudentRequestDTO studentRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(studentRequestDTO.getMatricule().equals("") || studentRequestDTO.getFirstname().equals("")
                || studentRequestDTO.getLevelId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Student studentLast = studentRepository.findById(studentId).orElse(null);
        if(studentLast == null) throw new RessourceNotFoundException("Student not exist!");
        //Vérifier si le Level Id passé en paramètre existe vraiment
        Level level = levelRepository.findById(studentRequestDTO.getLevelId()).orElse(null);
        if(level == null) throw new RessourceNotFoundException("Level Not Found for this levelId!");
        //S'assurer que le matricule n'existe pas s'il a été modifié
        if(!studentRequestDTO.getMatricule().equals(studentLast.getMatricule())){
            Student aC = studentRepository.findByMatricule(studentRequestDTO.getMatricule());
            if(aC!=null) throw new RessourceNotFoundException("Matricule already exist");
        }
        //Faire la sauvegarde
        Student student = studentMapper.studentRequestDTOToStudent(studentRequestDTO);
        student.setId(studentId);
        student.setCreatedAt(studentLast.getCreatedAt());
        student.setUpdatedAt(Instant.now());
        return studentMapper.studentToStudentResponseDTO(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student != null){
            if(!requestRepository.findByStudent(student).isEmpty())
                throw new RessourceNotFoundException("Error : Requests Exist for this Student Id!");
            studentRepository.deleteById(studentId);
        }
        else throw new RessourceNotFoundException("Student Not Exist!");
    }
}
