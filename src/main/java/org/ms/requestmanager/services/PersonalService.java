package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface PersonalService {
    PersonalResponseDTO savePersonal(PersonalRequestDTO personalRequestDTO);
    PersonalResponseDTO getPersonal(Long personalId);
    List<PersonalResponseDTO> getAllPersonals();
    List<PersonalResponseDTO> getAllPersonalsByTypePersonal(Long typePersonalId);
    List<PersonalResponseDTO> getAllPersonalsByDepartment(Long departmentId);
    PersonalResponseDTO updatePersonal(Long personalId, PersonalRequestDTO personalRequestDTO);
    void deletePersonal(Long personalId);
}
