package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDTO saveDepartment(DepartmentRequestDTO departmentRequestDTO);
    DepartmentResponseDTO getDepartment(Long departmentId);
    List<DepartmentResponseDTO> getAllDepartments();
    DepartmentResponseDTO updateDepartment(Long departmentId, DepartmentRequestDTO departmentRequestDTO);
    void deleteDepartment(Long departmentId);
}
