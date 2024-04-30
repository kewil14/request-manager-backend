package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.TypeRessourceRequestDTO;
import org.ms.requestmanager.dto.TypeRessourceResponseDTO;
import org.ms.requestmanager.entities.TypeRessource;

@Mapper(componentModel = "spring")
public interface TypeRessourceMapper {
    TypeRessource typeRessourceRequestDTOToTypeRessource(TypeRessourceRequestDTO typeRessourceRequestDTO);
    TypeRessourceResponseDTO typeRessourceToTypeRessourceResponseDTO(TypeRessource typeRessource);
}
