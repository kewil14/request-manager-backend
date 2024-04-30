package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface TypePersonalService {
    TypePersonalResponseDTO saveTypePersonal(TypePersonalRequestDTO typePersonalRequestDTO);
    TypePersonalResponseDTO getTypePersonal(Long typePersonalId);
    List<TypePersonalResponseDTO> getAllTypePersonals();
    TypePersonalResponseDTO updateTypePersonal(Long typePersonalId, TypePersonalRequestDTO typePersonalRequestDTO);
    void deleteTypePersonal(Long typePersonalId);
    void run(String... args) throws Exception;
}
