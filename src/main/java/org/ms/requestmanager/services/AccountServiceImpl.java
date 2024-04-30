package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;
import org.ms.requestmanager.entities.*;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.*;
import org.ms.requestmanager.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService, CommandLineRunner {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserMapper appUserMapper;
    private final AppRoleMapper appRoleMapper;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder, AppUserMapper appUserMapper, AppRoleMapper appRoleMapper) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserMapper = appUserMapper;
        this.appRoleMapper = appRoleMapper;
    }

    @Override
    public AppUserResponseDTO addNewUser(AppUserRequestDTO appUserRequestDTO) {
        if(appUserRequestDTO.getUsername().equals("") ||
            appUserRequestDTO.getPassword().equals("") ||
            appUserRequestDTO.getConfirmedPassword().equals(""))
            throw new RessourceNotFoundException("Données requises non reçues");
        if (!appUserRequestDTO.getPassword().equals(appUserRequestDTO.getConfirmedPassword()))
            throw new RessourceNotFoundException("Les mots de passe ne correspondent pas.");
        if(appUserRepository.findByUsername(appUserRequestDTO.getUsername())!=null)
            throw new RessourceNotFoundException("Utilisateur existant");
        AppUser appUser = appUserMapper.AppUserRequestDTOToAppUser(appUserRequestDTO);
        appUser.setId(UUID.randomUUID().toString());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserMapper.AppUserToAppUserResponseDTO(appUserRepository.save(appUser));
    }

    @Override
    public AppRoleResponseDTO addNewRole(AppRoleRequestDTO appRoleRequestDTO) {
        if (appRoleRequestDTO.getRolename().equals(""))
            throw new RessourceNotFoundException("Aucune donnée reçue");
        if(appRoleRepository.findByRolename(appRoleRequestDTO.getRolename())!=null)
            throw new RessourceNotFoundException("Role existant");
        AppRole appRole = appRoleMapper.AppRoleRequestTOAppRole(appRoleRequestDTO);
        appRole.setId(UUID.randomUUID().toString());
        return appRoleMapper.AppRoleToAppRoleResponseDTO(appRoleRepository.save(appRole));
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        if (username.equals("") || rolename.equals(""))
            throw new RessourceNotFoundException("Données requises non reçues");
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null)
            throw new RessourceNotFoundException("Utilisateur inconnu");
        AppRole appRole = appRoleRepository.findByRolename(rolename);
        if(appRole == null)
            throw new RessourceNotFoundException("Rôle inconnu");
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUserResponseDTO loadUserByUsername(String username) {
        return appUserMapper.AppUserToAppUserResponseDTO(appUserRepository.findByUsername(username));
    }

    @Override
    public List<AppUserResponseDTO> listUsers() {
        List<AppUser> appUsers = appUserRepository.findAll();
        return appUsers.stream()
                .map(appUserMapper::AppUserToAppUserResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppRoleResponseDTO> listRoles() {
        List<AppRole> appRoles = appRoleRepository.findAll();
        return appRoles.stream()
                .map(appRoleMapper::AppRoleToAppRoleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void run(String... args) {
        if(appRoleRepository.findAll().isEmpty()){
            //Roles
            addNewRole(new AppRoleRequestDTO("STUDENT"));
            addNewRole(new AppRoleRequestDTO("PERSONAL"));
            addNewRole(new AppRoleRequestDTO("ADMIN"));
            //Users
            addNewUser(new AppUserRequestDTO("student1","1234","1234"));
            addNewUser(new AppUserRequestDTO("teacher1","1234","1234"));
            addNewUser(new AppUserRequestDTO("teacher2","1234","1234"));
            addNewUser(new AppUserRequestDTO("teacher3","1234","1234"));
            addNewUser(new AppUserRequestDTO("responsable1","1234","1234"));
            addNewUser(new AppUserRequestDTO("responsable2","1234","1234"));
            addNewUser(new AppUserRequestDTO("responsable3","1234","1234"));
            addNewUser(new AppUserRequestDTO("secretary","1234","1234"));
            addNewUser(new AppUserRequestDTO("admin","1234","1234"));
            //Role to Users
            addRoleToUser("student1","STUDENT");
            addRoleToUser("teacher1","PERSONAL");
            addRoleToUser("teacher2","PERSONAL");
            addRoleToUser("teacher2","PERSONAL");
            addRoleToUser("teacher3","PERSONAL");
            addRoleToUser("responsable1","PERSONAL");
            addRoleToUser("responsable2","PERSONAL");
            addRoleToUser("responsable3","PERSONAL");
            addRoleToUser("secretary","PERSONAL");
            addRoleToUser("admin","ADMIN");
        }
    }
}
