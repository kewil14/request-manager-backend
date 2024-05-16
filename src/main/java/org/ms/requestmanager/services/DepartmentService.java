package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDTO saveDepartment(DepartmentRequestDTO departmentRequestDTO);
    DepartmentResponseDTO getDepartment(Long departmentId);
    DepartmentResponseDTO defineTimeTableDepartment(MultipartFile file, Long departmentId, int semester);
    ResponseEntity<byte[]> getTimetableDepartment(Long departmentId, int semester);
    List<DepartmentResponseDTO> getAllDepartments();
    DepartmentResponseDTO updateDepartment(Long departmentId, DepartmentRequestDTO departmentRequestDTO);
    void deleteDepartment(Long departmentId);
}
