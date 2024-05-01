package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.PersonalRequestDTO;
import org.ms.requestmanager.dto.PersonalResponseDTO;
import org.ms.requestmanager.entities.Personal;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T08:50:54+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class PersonalMapperImpl implements PersonalMapper {

    @Override
    public Personal personalRequestDTOToPersonal(PersonalRequestDTO personalRequestDTO) {
        if ( personalRequestDTO == null ) {
            return null;
        }

        Personal personal = new Personal();

        personal.setGrade( personalRequestDTO.getGrade() );
        personal.setFirstname( personalRequestDTO.getFirstname() );
        personal.setLastname( personalRequestDTO.getLastname() );

        return personal;
    }

    @Override
    public PersonalResponseDTO personalToPersonalResponseDTO(Personal personal) {
        if ( personal == null ) {
            return null;
        }

        PersonalResponseDTO personalResponseDTO = new PersonalResponseDTO();

        personalResponseDTO.setId( personal.getId() );
        personalResponseDTO.setGrade( personal.getGrade() );
        personalResponseDTO.setFirstname( personal.getFirstname() );
        personalResponseDTO.setLastname( personal.getLastname() );
        personalResponseDTO.setAppUser( personal.getAppUser() );
        personalResponseDTO.setTypePersonal( personal.getTypePersonal() );
        personalResponseDTO.setDepartment( personal.getDepartment() );
        personalResponseDTO.setCreatedAt( personal.getCreatedAt() );
        personalResponseDTO.setUpdatedAt( personal.getUpdatedAt() );

        return personalResponseDTO;
    }
}
