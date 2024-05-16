package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.DepartmentRequestDTO;
import org.ms.requestmanager.dto.DepartmentResponseDTO;
import org.ms.requestmanager.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class DepartmentRestAPI {
    private final DepartmentService departmentService;

    public DepartmentRestAPI(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @PostMapping(path = "/departments")
    public DepartmentResponseDTO save(@RequestBody DepartmentRequestDTO departmentRequestDTO){
        return departmentService.saveDepartment(departmentRequestDTO);
    }
    @PutMapping(path = "/departments/{id}/timetable/{sem}")
    public DepartmentResponseDTO defineTimeTable(@RequestParam("file") MultipartFile file, @PathVariable Long id, @PathVariable int sem) {
        return departmentService.defineTimeTableDepartment(file, id, sem);
    }
    @GetMapping(path = "/departments/{id}")
    public DepartmentResponseDTO getDepartment(@PathVariable Long id){
        return departmentService.getDepartment(id);
    }
    @GetMapping(path = "/departments/{id}/timetable/{sem}")
    public ResponseEntity<byte[]> getTimetableDepartment(@PathVariable Long id, @PathVariable int sem) {
        return departmentService.getTimetableDepartment( id, sem);
    }
    @GetMapping(path = "/departments")
    public List<DepartmentResponseDTO> allDepartments(){
        return departmentService.getAllDepartments();
    }
    @PutMapping(path = "/departments/{id}")
    public DepartmentResponseDTO updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequestDTO departmentRequestDTO){
        return departmentService.updateDepartment(id, departmentRequestDTO);
    }
    @DeleteMapping(path = "/departments/{id}")
    public void delete(@PathVariable Long id){
        departmentService.deleteDepartment(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}