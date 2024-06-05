package org.ms.requestmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ms.requestmanager.dto.StudentRequestDTO;
import org.ms.requestmanager.dto.StudentResponseDTO;
import org.ms.requestmanager.entities.AppUser;
import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Student;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.StudentMapper;
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
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final LevelRepository levelRepository;
    private final RequestRepository requestRepository;
    private final AppUserRepository appUserRepository;
    private final FileService fileService;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, LevelRepository levelRepository, RequestRepository requestRepository, AppUserRepository appUserRepository, FileService fileService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.levelRepository = levelRepository;
        this.requestRepository = requestRepository;
        this.appUserRepository = appUserRepository;
        this.fileService = fileService;
    }

    @Override
    public StudentResponseDTO saveStudent(MultipartFile file, String studentIn) throws JsonProcessingException {
        StudentRequestDTO studentRequestDTO = new ObjectMapper().readValue(studentIn, StudentRequestDTO.class);
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
        //check User
        AppUser appUser = null;
        if(studentRequestDTO.getUserId()!=null){
            appUser = appUserRepository.findById(studentRequestDTO.getUserId()).orElse(null);
            if(appUser == null) throw new RessourceNotFoundException("User Not Found for this UserId!");
            //Vérifier si l'user reçu n'est pas déjà associé
            Student s = studentRepository.findByAppUser(appUser);
            if(s!=null) throw new RessourceNotFoundException("User has already linked to Student");
        }
        //Faire le mapping et enregistrer
        Student student = studentMapper.studentRequestDTOToStudent(studentRequestDTO);
        student.setCreatedAt(Instant.now());
        student.setLevel(level);
        student.setAppUser(appUser);
        //Sauvegarde du fichier
        String extensionFile = fileService.getFileExtension(file.getOriginalFilename());
        if(!(extensionFile.equals("jpg") || extensionFile.equals("jpeg") || extensionFile.equals("png") || extensionFile.equals("svg")))
            throw new RessourceNotFoundException("Unsupported file extension ! Good Extension : jpg, jpeg, png & svg.");
        String fileName = "picture_"+
                student.getFirstname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                student.getLastname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                new Date().getTime()+"."+extensionFile;
        fileService.storeFile(file, fileName, "files/pictures");
        student.setPicture(fileName);
        return studentMapper.studentToStudentResponseDTO(studentRepository.save(student));
    }

    @Override
    public StudentResponseDTO getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student Not Found!");
        return studentMapper.studentToStudentResponseDTO(student);
    }

    @Override
    public ResponseEntity<byte[]> getStudentPicture(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("student Not Found!");
        if(student.getPicture() == null) throw new RessourceNotFoundException("No picture for this student!");
        return fileService.getFile("files/pictures", student.getPicture());
    }

    @Override
    public Path getStudentPathPicture(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("student Not Found!");
        if(student.getPicture() == null) throw new RessourceNotFoundException("No picture for this student!");
        return fileService.getPathFile("files/pictures", student.getPicture());
    }

    @Override
    public StudentResponseDTO getStudentByUser(String userId) {
        AppUser appUser = appUserRepository.findById(userId).orElse(null);
        Student student = studentRepository.findByAppUser(appUser);
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
        //check User
        AppUser appUser = null;
        if(studentRequestDTO.getUserId()!=null){
            appUser = appUserRepository.findById(studentRequestDTO.getUserId()).orElse(null);
            if(appUser == null) throw new RessourceNotFoundException("User Not Found for this UserId!");
            //Vérifier si l'user reçu n'est pas déjà associé s'il a été modifié
            if(!(appUser == studentLast.getAppUser())){
                Student s = studentRepository.findByAppUser(appUser);
                if(s!=null) throw new RessourceNotFoundException("User has already linked to Student");
            }
        }
        //Faire la sauvegarde
        Student student = studentMapper.studentRequestDTOToStudent(studentRequestDTO);
        student.setId(studentId);
        student.setCreatedAt(studentLast.getCreatedAt());
        student.setUpdatedAt(Instant.now());
        student.setAppUser(appUser);
        student.setLevel(level);
        return studentMapper.studentToStudentResponseDTO(studentRepository.save(student));
    }

    @Override
    public StudentResponseDTO updateStudentPicture(Long studentId, MultipartFile file) {
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student not exist!");
        //Sauvegarde du fichier
        String extensionFile = fileService.getFileExtension(file.getOriginalFilename());
        if(!(extensionFile.equals("jpg") || extensionFile.equals("jpeg") || extensionFile.equals("png") || extensionFile.equals("svg")))
            throw new RessourceNotFoundException("Unsupported file extension ! Good Extension : jpg, jpeg, png & svg.");
        String fileName = "picture_"+
                student.getFirstname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                student.getLastname().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                new Date().getTime()+"."+extensionFile;
        if(student.getPicture() != null) fileService.deleteFile(student.getPicture(), "files/pictures");
        fileService.storeFile(file, fileName, "files/pictures");
        student.setPicture(fileName);
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
