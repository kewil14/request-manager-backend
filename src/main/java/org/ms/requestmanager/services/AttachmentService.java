package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.AttachmentRequestDTO;
import org.ms.requestmanager.dto.AttachmentResponseDTO;

import java.util.List;

public interface AttachmentService {
    AttachmentResponseDTO saveAttachment(AttachmentRequestDTO attachmentRequestDTO);
    AttachmentResponseDTO getAttachment(Long attachmentId);
    List<AttachmentResponseDTO> getAllAttachments();
    List<AttachmentResponseDTO> getAllAttachmentsByRequest(Long requestId);
    AttachmentResponseDTO updateAttachment(Long attachmentId, AttachmentRequestDTO attachmentRequestDTO);
    void deleteAttachment(Long attachmentId);
}
