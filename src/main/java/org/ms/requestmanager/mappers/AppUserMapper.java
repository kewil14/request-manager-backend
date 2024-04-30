package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.AppUserRequestDTO;
import org.ms.requestmanager.dto.AppUserResponseDTO;
import org.ms.requestmanager.entities.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUser AppUserRequestDTOToAppUser(AppUserRequestDTO appUserRequestDTO);
    AppUserResponseDTO AppUserToAppUserResponseDTO(AppUser appUser);
}
