package org.ms.requestmanager.mappers;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Generated;
import org.ms.requestmanager.dto.AppUserRequestDTO;
import org.ms.requestmanager.dto.AppUserResponseDTO;
import org.ms.requestmanager.entities.AppRole;
import org.ms.requestmanager.entities.AppUser;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-29T06:35:57+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class AppUserMapperImpl implements AppUserMapper {

    @Override
    public AppUser AppUserRequestDTOToAppUser(AppUserRequestDTO appUserRequestDTO) {
        if ( appUserRequestDTO == null ) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setUsername( appUserRequestDTO.getUsername() );
        appUser.setPassword( appUserRequestDTO.getPassword() );

        return appUser;
    }

    @Override
    public AppUserResponseDTO AppUserToAppUserResponseDTO(AppUser appUser) {
        if ( appUser == null ) {
            return null;
        }

        AppUserResponseDTO appUserResponseDTO = new AppUserResponseDTO();

        appUserResponseDTO.setId( appUser.getId() );
        appUserResponseDTO.setUsername( appUser.getUsername() );
        Collection<AppRole> collection = appUser.getAppRoles();
        if ( collection != null ) {
            appUserResponseDTO.setAppRoles( new ArrayList<AppRole>( collection ) );
        }

        return appUserResponseDTO;
    }
}
