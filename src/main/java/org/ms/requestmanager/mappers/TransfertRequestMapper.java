package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.TransfertRequestRequestDTO;
import org.ms.requestmanager.dto.TransfertRequestResponseDTO;
import org.ms.requestmanager.entities.TransfertRequest;

@Mapper(componentModel = "spring")
public interface TransfertRequestMapper {
    TransfertRequest transfertRequestRequestDTOToTransfertRequest(TransfertRequestRequestDTO transfertRequestRequestDTO);
    TransfertRequestResponseDTO transfertRequestToTransfertRequestResponseDTO(TransfertRequest transfertRequest);
}
