package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.DepartmentRequestDTO;
import org.ms.requestmanager.dto.DepartmentResponseDTO;
import org.ms.requestmanager.entities.Department;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
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
        departmentResponseDTO.setLinkTimetableS1( department.getLinkTimetableS1() );
        departmentResponseDTO.setLinkTimetableS2( department.getLinkTimetableS2() );
        departmentResponseDTO.setPersonal( department.getPersonal() );
        departmentResponseDTO.setCreatedAt( department.getCreatedAt() );
        departmentResponseDTO.setUpdatedAt( department.getUpdatedAt() );

        return departmentResponseDTO;
    }
}
