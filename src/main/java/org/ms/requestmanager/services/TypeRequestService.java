package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface TypeRequestService {
    TypeRequestResponseDTO saveTypeRequest(TypeRequestRequestDTO typeRequestRequestDTO);
    TypeRequestResponseDTO getTypeRequest(Long typeRequestId);
    List<TypeRequestResponseDTO> getAllTypeRequests();
    TypeRequestResponseDTO updateTypeRequest(Long typeRequestId, TypeRequestRequestDTO typeRequestRequestDTO);
    void deleteTypeRequest(Long typeRequestId);
    void run(String... args) throws Exception;
}
