package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.TypePersonalRequestDTO;
import org.ms.requestmanager.dto.TypePersonalResponseDTO;
import org.ms.requestmanager.entities.TypePersonal;

@Mapper(componentModel = "spring")
public interface TypePersonalMapper {
    TypePersonal typePersonalRequestDTOToTypePersonal(TypePersonalRequestDTO typePersonalRequestDTO);
    TypePersonalResponseDTO typePersonalToTypePersonalResponseDTO(TypePersonal typePersonal);
}
