package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.RequestRequestDTO;
import org.ms.requestmanager.dto.RequestResponseDTO;
import org.ms.requestmanager.entities.Request;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-30T07:56:17+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class RequestMapperImpl implements RequestMapper {

    @Override
    public Request requestRequestDTOToRequest(RequestRequestDTO requestRequestDTO) {
        if ( requestRequestDTO == null ) {
            return null;
        }

        Request request = new Request();

        request.setObjet( requestRequestDTO.getObjet() );
        request.setMessage( requestRequestDTO.getMessage() );

        return request;
    }

    @Override
    public RequestResponseDTO requestToRequestResponseDTO(Request request) {
        if ( request == null ) {
            return null;
        }

        RequestResponseDTO requestResponseDTO = new RequestResponseDTO();

        requestResponseDTO.setId( request.getId() );
        requestResponseDTO.setObjet( request.getObjet() );
        requestResponseDTO.setMessage( request.getMessage() );
        requestResponseDTO.setStatus( request.getStatus() );
        requestResponseDTO.setState( request.getState() );
        requestResponseDTO.setTypeRequest( request.getTypeRequest() );
        requestResponseDTO.setStudent( request.getStudent() );
        requestResponseDTO.setPersonal( request.getPersonal() );
        requestResponseDTO.setUe( request.getUe() );
        requestResponseDTO.setCreatedAt( request.getCreatedAt() );
        requestResponseDTO.setUpdatedAt( request.getUpdatedAt() );

        return requestResponseDTO;
    }
}
