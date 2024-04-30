package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.TypeRequestRequestDTO;
import org.ms.requestmanager.dto.TypeRequestResponseDTO;
import org.ms.requestmanager.entities.TypeRequest;

@Mapper(componentModel = "spring")
public interface TypeRequestMapper {
    TypeRequest typeRequestRequestDTOToTypeRequest(TypeRequestRequestDTO typeRequestRequestDTO);
    TypeRequestResponseDTO typeRequestToTypeRequestResponseDTO(TypeRequest typeRequest);
}
