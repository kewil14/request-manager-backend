package org.ms.requestmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface AttachmentService {
    AttachmentResponseDTO saveAttachment(MultipartFile file, String attachmentIn) throws JsonProcessingException;
    AttachmentResponseDTO getAttachment(Long attachmentId);
    ResponseEntity<byte[]> getAttachmentFile(Long attachmentId);
    Path getAttachmentPathFile(Long attachmentId);
    List<AttachmentResponseDTO> getAllAttachments();
    List<AttachmentResponseDTO> getAllAttachmentsByRequest(Long requestId);
    AttachmentResponseDTO updateAttachment(Long attachmentId, AttachmentRequestDTO attachmentRequestDTO);
    AttachmentResponseDTO updateAttachmentFile(Long attachmentId, MultipartFile file);
    void deleteAttachment(Long attachmentId);
}