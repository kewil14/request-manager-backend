package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.AppRoleRequestDTO;
import org.ms.requestmanager.dto.AppRoleResponseDTO;
import org.ms.requestmanager.entities.AppRole;

@Mapper(componentModel = "spring")
public interface AppRoleMapper {
    AppRole AppRoleRequestTOAppRole(AppRoleRequestDTO appRoleRequestDTO);
    AppRoleResponseDTO AppRoleToAppRoleResponseDTO(AppRole appRole);
}
