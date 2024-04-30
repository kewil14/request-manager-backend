package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.DepartmentRequestDTO;
import org.ms.requestmanager.dto.DepartmentResponseDTO;
import org.ms.requestmanager.entities.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    Department departmentRequestDTOToDepartment(DepartmentRequestDTO departmentRequestDTO);
    DepartmentResponseDTO departmentToDepartmentResponseDTO(Department department);
}
