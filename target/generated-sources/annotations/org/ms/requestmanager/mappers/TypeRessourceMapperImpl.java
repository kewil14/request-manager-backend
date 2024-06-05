package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.TypeRessourceRequestDTO;
import org.ms.requestmanager.dto.TypeRessourceResponseDTO;
import org.ms.requestmanager.entities.TypeRessource;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class TypeRessourceMapperImpl implements TypeRessourceMapper {

    @Override
    public TypeRessource typeRessourceRequestDTOToTypeRessource(TypeRessourceRequestDTO typeRessourceRequestDTO) {
        if ( typeRessourceRequestDTO == null ) {
            return null;
        }

        TypeRessource typeRessource = new TypeRessource();

        typeRessource.setName( typeRessourceRequestDTO.getName() );

        return typeRessource;
    }

    @Override
    public TypeRessourceResponseDTO typeRessourceToTypeRessourceResponseDTO(TypeRessource typeRessource) {
        if ( typeRessource == null ) {
            return null;
        }

        TypeRessourceResponseDTO typeRessourceResponseDTO = new TypeRessourceResponseDTO();

        typeRessourceResponseDTO.setId( typeRessource.getId() );
        typeRessourceResponseDTO.setName( typeRessource.getName() );
        typeRessourceResponseDTO.setCreatedAt( typeRessource.getCreatedAt() );
        typeRessourceResponseDTO.setUpdatedAt( typeRessource.getUpdatedAt() );

        return typeRessourceResponseDTO;
    }
}
