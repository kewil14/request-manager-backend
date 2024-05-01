package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.AttachmentRequestDTO;
import org.ms.requestmanager.dto.AttachmentResponseDTO;
import org.ms.requestmanager.services.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class AttachmentRestAPI {
    private final AttachmentService attachmentService;

    public AttachmentRestAPI(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }
    @PostMapping(path = "/attachments")
    public AttachmentResponseDTO save(@RequestBody AttachmentRequestDTO attachmentRequestDTO){
        return attachmentService.saveAttachment(attachmentRequestDTO);
    }
    @GetMapping(path = "/attachments/{id}")
    public AttachmentResponseDTO getAttachment(@PathVariable Long id){
        return attachmentService.getAttachment(id);
    }
    @GetMapping(path = "/attachments")
    public List<AttachmentResponseDTO> allAttachments(){
        return attachmentService.getAllAttachments();
    }
    @GetMapping(path = "/attachments/request/{requestId}")
    public List<AttachmentResponseDTO> allAttachmentsByRequest(@PathVariable Long requestId){
        return attachmentService.getAllAttachmentsByRequest(requestId);
    }
    @PutMapping(path = "/attachments/{id}")
    public AttachmentResponseDTO updateAttachment(@PathVariable Long id, @RequestBody AttachmentRequestDTO attachmentRequestDTO){
        return attachmentService.updateAttachment(id, attachmentRequestDTO);
    }
    @DeleteMapping(path = "/attachments/{id}")
    public void delete(@PathVariable Long id){
        attachmentService.deleteAttachment(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}