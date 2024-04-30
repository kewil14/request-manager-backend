package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface CommentRequestService {
    CommentRequestResponseDTO saveCommentRequest(CommentRequestRequestDTO commentRequestRequestDTO);
    CommentRequestResponseDTO getCommentRequest(Long commentRequestId);
    List<CommentRequestResponseDTO> getAllCommentRequests();
    List<CommentRequestResponseDTO> getAllCommentRequestsByRequest(Long requestId);
    List<CommentRequestResponseDTO> getAllCommentRequestsByPersonal(Long personalId);
    CommentRequestResponseDTO updateCommentRequest(Long commentRequestId, CommentRequestRequestDTO commentRequestRequestDTO);
    void deleteCommentRequest(Long commentRequestId);
}
