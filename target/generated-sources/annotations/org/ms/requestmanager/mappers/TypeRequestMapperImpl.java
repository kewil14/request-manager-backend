package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.TypeRequestRequestDTO;
import org.ms.requestmanager.dto.TypeRequestResponseDTO;
import org.ms.requestmanager.entities.TypeRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class TypeRequestMapperImpl implements TypeRequestMapper {

    @Override
    public TypeRequest typeRequestRequestDTOToTypeRequest(TypeRequestRequestDTO typeRequestRequestDTO) {
        if ( typeRequestRequestDTO == null ) {
            return null;
        }

        TypeRequest typeRequest = new TypeRequest();

        typeRequest.setName( typeRequestRequestDTO.getName() );

        return typeRequest;
    }

    @Override
    public TypeRequestResponseDTO typeRequestToTypeRequestResponseDTO(TypeRequest typeRequest) {
        if ( typeRequest == null ) {
            return null;
        }

        TypeRequestResponseDTO typeRequestResponseDTO = new TypeRequestResponseDTO();

        typeRequestResponseDTO.setId( typeRequest.getId() );
        typeRequestResponseDTO.setName( typeRequest.getName() );
        typeRequestResponseDTO.setCreatedAt( typeRequest.getCreatedAt() );
        typeRequestResponseDTO.setUpdatedAt( typeRequest.getUpdatedAt() );

        return typeRequestResponseDTO;
    }
}
