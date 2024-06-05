package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.UeRequestDTO;
import org.ms.requestmanager.dto.UeResponseDTO;
import org.ms.requestmanager.entities.Ue;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class UeMapperImpl implements UeMapper {

    @Override
    public Ue ueRequestDTOToUe(UeRequestDTO ueRequestDTO) {
        if ( ueRequestDTO == null ) {
            return null;
        }

        Ue ue = new Ue();

        ue.setCode( ueRequestDTO.getCode() );
        ue.setTitle( ueRequestDTO.getTitle() );
        ue.setSemester( ueRequestDTO.getSemester() );

        return ue;
    }

    @Override
    public UeResponseDTO ueToUeResponseDTO(Ue ue) {
        if ( ue == null ) {
            return null;
        }

        UeResponseDTO ueResponseDTO = new UeResponseDTO();

        ueResponseDTO.setId( ue.getId() );
        ueResponseDTO.setCode( ue.getCode() );
        ueResponseDTO.setTitle( ue.getTitle() );
        ueResponseDTO.setSemester( ue.getSemester() );
        ueResponseDTO.setLevel( ue.getLevel() );
        ueResponseDTO.setPersonal( ue.getPersonal() );
        ueResponseDTO.setCreatedAt( ue.getCreatedAt() );
        ueResponseDTO.setUpdatedAt( ue.getUpdatedAt() );

        return ueResponseDTO;
    }
}
