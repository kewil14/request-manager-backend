package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.CommentRequestRequestDTO;
import org.ms.requestmanager.dto.CommentRequestResponseDTO;
import org.ms.requestmanager.entities.CommentRequest;

@Mapper(componentModel = "spring")
public interface CommentRequestMapper {
    CommentRequest commentRequestRequestDTOToCommentRequest(CommentRequestRequestDTO commentRequestRequestDTO);
    CommentRequestResponseDTO commentRequestToCommentRequestResponseDTO(CommentRequest commentRequest);
}
