package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.TypePersonalRequestDTO;
import org.ms.requestmanager.dto.TypePersonalResponseDTO;
import org.ms.requestmanager.entities.TypePersonal;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T08:50:53+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class TypePersonalMapperImpl implements TypePersonalMapper {

    @Override
    public TypePersonal typePersonalRequestDTOToTypePersonal(TypePersonalRequestDTO typePersonalRequestDTO) {
        if ( typePersonalRequestDTO == null ) {
            return null;
        }

        TypePersonal typePersonal = new TypePersonal();

        typePersonal.setName( typePersonalRequestDTO.getName() );

        return typePersonal;
    }

    @Override
    public TypePersonalResponseDTO typePersonalToTypePersonalResponseDTO(TypePersonal typePersonal) {
        if ( typePersonal == null ) {
            return null;
        }

        TypePersonalResponseDTO typePersonalResponseDTO = new TypePersonalResponseDTO();

        typePersonalResponseDTO.setId( typePersonal.getId() );
        typePersonalResponseDTO.setName( typePersonal.getName() );
        typePersonalResponseDTO.setCreatedAt( typePersonal.getCreatedAt() );
        typePersonalResponseDTO.setUpdatedAt( typePersonal.getUpdatedAt() );

        return typePersonalResponseDTO;
    }
}
