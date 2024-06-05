package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.AttachmentRequestDTO;
import org.ms.requestmanager.dto.AttachmentResponseDTO;
import org.ms.requestmanager.entities.Attachment;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class AttachmentMapperImpl implements AttachmentMapper {

    @Override
    public Attachment attachmentRequestDTOToAttachment(AttachmentRequestDTO attachmentRequestDTO) {
        if ( attachmentRequestDTO == null ) {
            return null;
        }

        Attachment attachment = new Attachment();

        attachment.setName( attachmentRequestDTO.getName() );

        return attachment;
    }

    @Override
    public AttachmentResponseDTO attachmentToAttachmentResponseDTO(Attachment attachment) {
        if ( attachment == null ) {
            return null;
        }

        AttachmentResponseDTO attachmentResponseDTO = new AttachmentResponseDTO();

        attachmentResponseDTO.setId( attachment.getId() );
        attachmentResponseDTO.setName( attachment.getName() );
        attachmentResponseDTO.setLink( attachment.getLink() );
        attachmentResponseDTO.setRequest( attachment.getRequest() );
        attachmentResponseDTO.setCreatedAt( attachment.getCreatedAt() );
        attachmentResponseDTO.setUpdatedAt( attachment.getUpdatedAt() );

        return attachmentResponseDTO;
    }
}
