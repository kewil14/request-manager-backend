package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface RequestService {
    RequestResponseDTO saveRequest(RequestRequestDTO requestRequestDTO);
    RequestResponseDTO getRequest(Long requestId);
    List<RequestResponseDTO> getAllRequests();
    List<RequestResponseDTO> getAllRequestsByTypeRequest(Long typeRequestId);
    List<RequestResponseDTO> getAllRequestsByStudent(Long studentId);
    List<RequestResponseDTO> getAllRequestsByPersonal(Long personalId);
    List<RequestResponseDTO> getAllRequestsByUe(Long ueId);
    RequestResponseDTO updateRequest(Long requestId, RequestRequestDTO requestRequestDTO);
    void archiveRequest(Long requestId);
    void updateStatusRequest(Long requestId, String status);
    void deleteRequest(Long requestId);
}
