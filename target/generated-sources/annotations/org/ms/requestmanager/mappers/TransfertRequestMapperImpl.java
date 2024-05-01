package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.TransfertRequestRequestDTO;
import org.ms.requestmanager.dto.TransfertRequestResponseDTO;
import org.ms.requestmanager.entities.TransfertRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T08:50:53+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class TransfertRequestMapperImpl implements TransfertRequestMapper {

    @Override
    public TransfertRequest transfertRequestRequestDTOToTransfertRequest(TransfertRequestRequestDTO transfertRequestRequestDTO) {
        if ( transfertRequestRequestDTO == null ) {
            return null;
        }

        TransfertRequest transfertRequest = new TransfertRequest();

        transfertRequest.setMessage( transfertRequestRequestDTO.getMessage() );

        return transfertRequest;
    }

    @Override
    public TransfertRequestResponseDTO transfertRequestToTransfertRequestResponseDTO(TransfertRequest transfertRequest) {
        if ( transfertRequest == null ) {
            return null;
        }

        TransfertRequestResponseDTO transfertRequestResponseDTO = new TransfertRequestResponseDTO();

        transfertRequestResponseDTO.setId( transfertRequest.getId() );
        transfertRequestResponseDTO.setMessage( transfertRequest.getMessage() );
        transfertRequestResponseDTO.setRequest( transfertRequest.getRequest() );
        transfertRequestResponseDTO.setPersonal( transfertRequest.getPersonal() );
        transfertRequestResponseDTO.setCreatedAt( transfertRequest.getCreatedAt() );
        transfertRequestResponseDTO.setUpdatedAt( transfertRequest.getUpdatedAt() );

        return transfertRequestResponseDTO;
    }
}
