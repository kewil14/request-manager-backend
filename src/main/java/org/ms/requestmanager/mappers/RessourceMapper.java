package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.RessourceRequestDTO;
import org.ms.requestmanager.dto.RessourceResponseDTO;
import org.ms.requestmanager.entities.Ressource;

@Mapper(componentModel = "spring")
public interface RessourceMapper {
    Ressource ressourceRequestDTOToRessource(RessourceRequestDTO ressourceRequestDTO);
    RessourceResponseDTO ressourceToRessourceResponseDTO(Ressource ressource);
}
