package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.RequestRequestDTO;
import org.ms.requestmanager.dto.RequestResponseDTO;
import org.ms.requestmanager.entities.Request;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    Request requestRequestDTOToRequest(RequestRequestDTO requestRequestDTO);
    RequestResponseDTO requestToRequestResponseDTO(Request request);
}
