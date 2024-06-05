package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.RessourceRequestDTO;
import org.ms.requestmanager.dto.RessourceResponseDTO;
import org.ms.requestmanager.entities.Ressource;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class RessourceMapperImpl implements RessourceMapper {

    @Override
    public Ressource ressourceRequestDTOToRessource(RessourceRequestDTO ressourceRequestDTO) {
        if ( ressourceRequestDTO == null ) {
            return null;
        }

        Ressource ressource = new Ressource();

        ressource.setName( ressourceRequestDTO.getName() );

        return ressource;
    }

    @Override
    public RessourceResponseDTO ressourceToRessourceResponseDTO(Ressource ressource) {
        if ( ressource == null ) {
            return null;
        }

        RessourceResponseDTO ressourceResponseDTO = new RessourceResponseDTO();

        ressourceResponseDTO.setId( ressource.getId() );
        ressourceResponseDTO.setName( ressource.getName() );
        ressourceResponseDTO.setLink( ressource.getLink() );
        ressourceResponseDTO.setTypeRessource( ressource.getTypeRessource() );
        ressourceResponseDTO.setUe( ressource.getUe() );
        ressourceResponseDTO.setCreatedAt( ressource.getCreatedAt() );
        ressourceResponseDTO.setUpdatedAt( ressource.getUpdatedAt() );

        return ressourceResponseDTO;
    }
}
