package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.CommentRequestRequestDTO;
import org.ms.requestmanager.dto.CommentRequestResponseDTO;
import org.ms.requestmanager.services.CommentRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CommentRequestRestAPI {
    private final CommentRequestService commentRequestService;

    public CommentRequestRestAPI(CommentRequestService commentRequestService) {
        this.commentRequestService = commentRequestService;
    }
    @PostMapping(path = "/commentRequests")
    public CommentRequestResponseDTO save(@RequestBody CommentRequestRequestDTO commentRequestRequestDTO){
        return commentRequestService.saveCommentRequest(commentRequestRequestDTO);
    }
    @GetMapping(path = "/commentRequests/{id}")
    public CommentRequestResponseDTO getCommentRequest(@PathVariable Long id){
        return commentRequestService.getCommentRequest(id);
    }
    @GetMapping(path = "/commentRequests")
    public List<CommentRequestResponseDTO> allCommentRequests(){
        return commentRequestService.getAllCommentRequests();
    }
    @GetMapping(path = "/commentRequests/request/{requestId}")
    public List<CommentRequestResponseDTO> allCommentRequestsByRequest(@PathVariable Long requestId){
        return commentRequestService.getAllCommentRequestsByRequest(requestId);
    }
    @GetMapping(path = "/commentRequests/personal/{personalId}")
    public List<CommentRequestResponseDTO> allCommentRequestsByPersonal(@PathVariable Long personalId){
        return commentRequestService.getAllCommentRequestsByPersonal(personalId);
    }
    @PutMapping(path = "/commentRequests/{id}")
    public CommentRequestResponseDTO updateCommentRequest(@PathVariable Long id, @RequestBody CommentRequestRequestDTO commentRequestRequestDTO){
        return commentRequestService.updateCommentRequest(id, commentRequestRequestDTO);
    }
    @DeleteMapping(path = "/commentRequests/{id}")
    public void delete(@PathVariable Long id){
        commentRequestService.deleteCommentRequest(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}