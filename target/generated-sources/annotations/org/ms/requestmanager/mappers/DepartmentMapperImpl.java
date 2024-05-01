package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.DepartmentRequestDTO;
import org.ms.requestmanager.dto.DepartmentResponseDTO;
import org.ms.requestmanager.entities.Department;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T08:50:54+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public Department departmentRequestDTOToDepartment(DepartmentRequestDTO departmentRequestDTO) {
        if ( departmentRequestDTO == null ) {
            return null;
        }

        Department department = new Department();

        department.setName( departmentRequestDTO.getName() );

        return department;
    }

    @Override
    public DepartmentResponseDTO departmentToDepartmentResponseDTO(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();

        departmentResponseDTO.setId( department.getId() );
        departmentResponseDTO.setName( department.getName() );
        departmentResponseDTO.setPersonal( department.getPersonal() );
        departmentResponseDTO.setCreatedAt( department.getCreatedAt() );
        departmentResponseDTO.setUpdatedAt( department.getUpdatedAt() );

        return departmentResponseDTO;
    }
}
