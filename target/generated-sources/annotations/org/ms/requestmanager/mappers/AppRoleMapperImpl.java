package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.AppRoleRequestDTO;
import org.ms.requestmanager.dto.AppRoleResponseDTO;
import org.ms.requestmanager.entities.AppRole;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class AppRoleMapperImpl implements AppRoleMapper {

    @Override
    public AppRole AppRoleRequestTOAppRole(AppRoleRequestDTO appRoleRequestDTO) {
        if ( appRoleRequestDTO == null ) {
            return null;
        }

        AppRole appRole = new AppRole();

        appRole.setRolename( appRoleRequestDTO.getRolename() );

        return appRole;
    }

    @Override
    public AppRoleResponseDTO AppRoleToAppRoleResponseDTO(AppRole appRole) {
        if ( appRole == null ) {
            return null;
        }

        AppRoleResponseDTO appRoleResponseDTO = new AppRoleResponseDTO();

        appRoleResponseDTO.setId( appRole.getId() );
        appRoleResponseDTO.setRolename( appRole.getRolename() );

        return appRoleResponseDTO;
    }
}
