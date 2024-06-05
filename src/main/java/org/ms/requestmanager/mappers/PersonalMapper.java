package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.PersonalRequestDTO;
import org.ms.requestmanager.dto.PersonalResponseDTO;
import org.ms.requestmanager.entities.Personal;

@Mapper(componentModel = "spring")
public interface PersonalMapper {
    Personal personalRequestDTOToPersonal(PersonalRequestDTO personalRequestDTO);
    PersonalResponseDTO personalToPersonalResponseDTO(Personal personal);
}
