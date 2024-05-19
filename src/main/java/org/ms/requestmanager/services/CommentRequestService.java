package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface CommentRequestService {
    CommentRequestResponseDTO saveCommentRequest(CommentRequestRequestDTO commentRequestRequestDTO, int mode);
    CommentRequestResponseDTO getCommentRequest(Long commentRequestId);
    List<CommentRequestResponseDTO> getAllCommentRequests();
    List<CommentRequestResponseDTO> getAllCommentRequestsByRequest(Long requestId);
    List<CommentRequestResponseDTO> getAllCommentRequestsByPersonal(Long personalId);
    List<CommentRequestResponseDTO> getAllCommentRequestsByStudent(Long studentId);
    List<CommentRequestResponseDTO> getAllCommentRequestsByRequestAndPersonal(Long requestId, Long personalId);
    List<CommentRequestResponseDTO> getAllCommentRequestsByRequestAndStudent(Long requestId, Long studentId);
    CommentRequestResponseDTO updateCommentRequest(Long commentRequestId, CommentRequestRequestDTO commentRequestRequestDTO, int mode);
    void deleteCommentRequest(Long commentRequestId);
}
