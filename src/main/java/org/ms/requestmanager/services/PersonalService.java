package org.ms.requestmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface PersonalService {
    PersonalResponseDTO savePersonal(MultipartFile file, String personalIn) throws JsonProcessingException;
    PersonalResponseDTO getPersonal(Long personalId);
    ResponseEntity<byte[]> getPersonalPicture(Long personalId);
    Path getPersonalPathPicture(Long personalId);
    PersonalResponseDTO getPersonalByUser(String userId);
    List<PersonalResponseDTO> getAllPersonals();
    List<PersonalResponseDTO> getAllPersonalsByTypePersonal(Long typePersonalId);
    List<PersonalResponseDTO> getAllPersonalsByDepartment(Long departmentId);
    PersonalResponseDTO updatePersonal(Long personalId, PersonalRequestDTO personalRequestDTO);
    PersonalResponseDTO updatePersonalPicture(Long personalId, MultipartFile file);
    void deletePersonal(Long personalId);
}
