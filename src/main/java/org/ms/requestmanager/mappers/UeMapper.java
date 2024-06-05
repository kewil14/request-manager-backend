package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.UeRequestDTO;
import org.ms.requestmanager.dto.UeResponseDTO;
import org.ms.requestmanager.entities.Ue;

@Mapper(componentModel = "spring")
public interface UeMapper {
    Ue ueRequestDTOToUe(UeRequestDTO ueRequestDTO);
    UeResponseDTO ueToUeResponseDTO(Ue ue);
}
