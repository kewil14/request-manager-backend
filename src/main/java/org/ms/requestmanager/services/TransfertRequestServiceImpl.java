package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.TransfertRequestRequestDTO;
import org.ms.requestmanager.dto.TransfertRequestResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransfertRequestServiceImpl implements TransfertRequestService {
    @Override
    public TransfertRequestResponseDTO saveTransfertRequest(TransfertRequestRequestDTO transfertRequestRequestDTO) {
        return null;
    }

    @Override
    public TransfertRequestResponseDTO getTransfertRequest(Long transfertRequestId) {
        return null;
    }

    @Override
    public List<TransfertRequestResponseDTO> getAllTransfertRequests() {
        return null;
    }

    @Override
    public List<TransfertRequestResponseDTO> getAllTransfertRequestsByRequest(Long requestId) {
        return null;
    }

    @Override
    public List<TransfertRequestResponseDTO> getAllTransfertRequestsByPersonal(Long personalId) {
        return null;
    }

    @Override
    public TransfertRequestResponseDTO updateTransfertRequest(Long transfertRequestId, TransfertRequestRequestDTO transfertRequestRequestDTO) {
        return null;
    }

    @Override
    public void deleteTransfertRequest(Long transfertRequestId) {

    }
}
