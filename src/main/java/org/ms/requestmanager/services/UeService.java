package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface UeService {
    UeResponseDTO saveUe(UeRequestDTO ueRequestDTO);
    UeResponseDTO getUe(Long ueId);
    List<UeResponseDTO> getAllUes();
    List<UeResponseDTO> getAllUesByLevel(Long levelId);
    List<UeResponseDTO> getAllUesByLevelAndSemester(Long levelId, Long semester);
    List<UeResponseDTO> getAllUesByPersonal(Long personalId);
    UeResponseDTO updateUe(Long ueId, UeRequestDTO ueRequestDTO);
    void deleteUe(Long ueId);
}
