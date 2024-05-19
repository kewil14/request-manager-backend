package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface TransfertRequestService {
    TransfertRequestResponseDTO saveTransfertRequest(TransfertRequestRequestDTO transfertRequestRequestDTO);
    TransfertRequestResponseDTO getTransfertRequest(Long transfertRequestId);
    List<TransfertRequestResponseDTO> getAllTransfertRequests();
    List<TransfertRequestResponseDTO> getAllTransfertRequestsByRequest(Long requestId);
    List<TransfertRequestResponseDTO> getAllTransfertRequestsByRequestAndPersonal(Long requestId, Long personalId);
    List<TransfertRequestResponseDTO> getAllTransfertRequestsByPersonal(Long personalId);
    TransfertRequestResponseDTO updateTransfertRequest(Long transfertRequestId, TransfertRequestRequestDTO transfertRequestRequestDTO);
    void deleteTransfertRequest(Long transfertRequestId);
}
