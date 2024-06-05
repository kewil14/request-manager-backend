package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.AppRoleRequestDTO;
import org.ms.requestmanager.dto.AppRoleResponseDTO;
import org.ms.requestmanager.dto.AppUserRequestDTO;
import org.ms.requestmanager.dto.AppUserResponseDTO;

import java.util.List;

public interface AccountService {
    AppUserResponseDTO addNewUser(AppUserRequestDTO appUserRequestDTO);
    AppRoleResponseDTO addNewRole(AppRoleRequestDTO appRoleRequestDTO);
    void addRoleToUser(String username, String rolename);
    AppUserResponseDTO loadUserByUsername(String username);
    List<AppUserResponseDTO> listUsers();
    List<AppRoleResponseDTO> listRoles();
    void run(String... args) throws Exception;
}
