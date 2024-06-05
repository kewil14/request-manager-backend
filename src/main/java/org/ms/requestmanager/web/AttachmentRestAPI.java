package org.ms.requestmanager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.AttachmentRequestDTO;
import org.ms.requestmanager.dto.AttachmentResponseDTO;
import org.ms.requestmanager.dto.RessourceResponseDTO;
import org.ms.requestmanager.services.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class AttachmentRestAPI {
    private final AttachmentService attachmentService;

    public AttachmentRestAPI(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }
    @PostMapping(path = "/attachments")
    public AttachmentResponseDTO save(@RequestParam("file") MultipartFile file, String attachmentIn) throws JsonProcessingException {
        return attachmentService.saveAttachment(file, attachmentIn);
    }
    @GetMapping(path = "/attachments/{id}")
    public AttachmentResponseDTO getAttachment(@PathVariable Long id){
        return attachmentService.getAttachment(id);
    }
    @GetMapping(path = "/attachments/file/{id}")
    public ResponseEntity<byte[]> getAttachmentFile(@PathVariable Long id) {
        return attachmentService.getAttachmentFile(id);
    }
    @GetMapping(path = "/attachments/pathfile/{id}")
    public Path getAttachmentPathFile(@PathVariable Long id) {
        return attachmentService.getAttachmentPathFile(id);
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
    @PutMapping(path = "/attachments/{id}/file")
    public AttachmentResponseDTO updateAttachmentFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return attachmentService.updateAttachmentFile(id, file);
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