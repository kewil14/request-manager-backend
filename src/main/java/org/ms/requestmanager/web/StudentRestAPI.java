package org.ms.requestmanager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.StudentResponseDTO;
import org.ms.requestmanager.dto.StudentRequestDTO;
import org.ms.requestmanager.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StudentRestAPI {
    private final StudentService studentService;

    public StudentRestAPI(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping(path = "/students")
    public StudentResponseDTO save(@RequestParam("file") MultipartFile file, String studentIn) throws JsonProcessingException {
        return studentService.saveStudent(file, studentIn);
    }
    @PostMapping(path = "/students/create")
    public StudentResponseDTO saveUser(@RequestParam("file") MultipartFile file, String studentIn) throws JsonProcessingException {
        return studentService.saveStudent(file, studentIn);
    }
    @GetMapping(path = "/students/{id}")
    public StudentResponseDTO getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
    }
    @GetMapping(path = "/students/picture/{id}")
    public ResponseEntity<byte[]> getStudentFile(@PathVariable Long id) {
        return studentService.getStudentPicture(id);
    }
    @GetMapping(path = "/students/pathpicture/{id}")
    public Path getStudentPathFile(@PathVariable Long id) {
        return studentService.getStudentPathPicture(id);
    }
    @GetMapping(path = "/students/ByUser/{id}")
    public StudentResponseDTO getStudentByUserId(@PathVariable String id){
        return studentService.getStudentByUser(id);
    }
    @GetMapping(path = "/students")
    public List<StudentResponseDTO> allStudents(){
        return studentService.getAllStudents();
    }
    @GetMapping(path = "/students/level/{levelId}")
    public List<StudentResponseDTO> allStudentsByLevel(@PathVariable Long levelId){
        return studentService.getAllStudentsByLevel(levelId);
    }
    @PutMapping(path = "/students/{id}")
    public StudentResponseDTO updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequestDTO){
        return studentService.updateStudent(id, studentRequestDTO);
    }
    @PutMapping(path = "/students/{id}/picture")
    public StudentResponseDTO updateStudentPicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return studentService.updateStudentPicture(id, file);
    }
    @DeleteMapping(path = "/students/{id}")
    public void delete(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}