package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.AttachmentRequestDTO;
import org.ms.requestmanager.dto.AttachmentResponseDTO;
import org.ms.requestmanager.entities.Attachment;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    Attachment attachmentRequestDTOToAttachment(AttachmentRequestDTO attachmentRequestDTO);
    AttachmentResponseDTO attachmentToAttachmentResponseDTO(Attachment attachment);
}
