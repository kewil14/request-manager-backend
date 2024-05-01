package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.CommentRequestRequestDTO;
import org.ms.requestmanager.dto.CommentRequestResponseDTO;
import org.ms.requestmanager.entities.CommentRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T08:50:54+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class CommentRequestMapperImpl implements CommentRequestMapper {

    @Override
    public CommentRequest commentRequestRequestDTOToCommentRequest(CommentRequestRequestDTO commentRequestRequestDTO) {
        if ( commentRequestRequestDTO == null ) {
            return null;
        }

        CommentRequest commentRequest = new CommentRequest();

        commentRequest.setMessage( commentRequestRequestDTO.getMessage() );

        return commentRequest;
    }

    @Override
    public CommentRequestResponseDTO commentRequestToCommentRequestResponseDTO(CommentRequest commentRequest) {
        if ( commentRequest == null ) {
            return null;
        }

        CommentRequestResponseDTO commentRequestResponseDTO = new CommentRequestResponseDTO();

        commentRequestResponseDTO.setId( commentRequest.getId() );
        commentRequestResponseDTO.setMessage( commentRequest.getMessage() );
        commentRequestResponseDTO.setRequest( commentRequest.getRequest() );
        commentRequestResponseDTO.setPersonal( commentRequest.getPersonal() );
        commentRequestResponseDTO.setCreatedAt( commentRequest.getCreatedAt() );
        commentRequestResponseDTO.setUpdatedAt( commentRequest.getUpdatedAt() );

        return commentRequestResponseDTO;
    }
}
