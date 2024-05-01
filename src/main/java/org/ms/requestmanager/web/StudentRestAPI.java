package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.StudentRequestDTO;
import org.ms.requestmanager.dto.StudentResponseDTO;
import org.ms.requestmanager.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StudentRestAPI {
    private final StudentService studentService;

    public StudentRestAPI(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping(path = "/students")
    public StudentResponseDTO save(@RequestBody StudentRequestDTO studentRequestDTO){
        return studentService.saveStudent(studentRequestDTO);
    }
    @GetMapping(path = "/students/{id}")
    public StudentResponseDTO getStudent(@PathVariable Long id){
        return studentService.getStudent(id);
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
    @DeleteMapping(path = "/students/{id}")
    public void delete(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}