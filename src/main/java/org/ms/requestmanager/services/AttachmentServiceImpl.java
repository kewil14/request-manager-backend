package org.ms.requestmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ms.requestmanager.dto.AttachmentRequestDTO;
import org.ms.requestmanager.dto.AttachmentResponseDTO;
import org.ms.requestmanager.entities.Attachment;
import org.ms.requestmanager.entities.Request;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.AttachmentMapper;
import org.ms.requestmanager.repositories.AttachmentRepository;
import org.ms.requestmanager.repositories.RequestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;
    private final RequestRepository requestRepository;
    private final FileService fileService;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper, RequestRepository requestRepository, FileService fileService) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
        this.requestRepository = requestRepository;
        this.fileService = fileService;
    }

    @Override
    public AttachmentResponseDTO saveAttachment(MultipartFile file, String attachmentIn) throws JsonProcessingException {
        AttachmentRequestDTO attachmentRequestDTO = new ObjectMapper().readValue(attachmentIn, AttachmentRequestDTO.class);
        //Vérifier que tous les paramètres ont été reçus
        if(attachmentRequestDTO.getName().equals("") || attachmentRequestDTO.getRequestId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le Request Id passé en paramètre existe vraiment
        Request request = requestRepository.findById(attachmentRequestDTO.getRequestId()).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        //Modifier le statut de la requête si nécessaire
        if(request.getStatus().equals("PENDING")){
            request.setStatus("SENDING");
            request.setUpdatedAt(Instant.now());
            requestRepository.save(request);
        }
        //Faire le mapping et enregistrer
        Attachment attachment = attachmentMapper.attachmentRequestDTOToAttachment(attachmentRequestDTO);
        attachment.setCreatedAt(Instant.now());
        attachment.setRequest(request);
        //Sauvegarde du fichier
        String extensionFile = fileService.getFileExtension(file.getOriginalFilename());
        if(!(extensionFile.equals("pdf")))
            throw new RessourceNotFoundException("Unsupported file extension ! Good Extension : PDF.");
        String fileName = "request_"+attachment.getRequest().getId()+"_attachment_"+
                attachment.getName().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                new Date().getTime()+"."+extensionFile;
        fileService.storeFile(file, fileName, "files/attachments");
        attachment.setLink(fileName);
        return attachmentMapper.attachmentToAttachmentResponseDTO(attachmentRepository.save(attachment));
    }

    @Override
    public AttachmentResponseDTO getAttachment(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment == null) throw new RessourceNotFoundException("Attachment Not Found!");
        return attachmentMapper.attachmentToAttachmentResponseDTO(attachment);
    }

    @Override
    public ResponseEntity<byte[]> getAttachmentFile(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment == null) throw new RessourceNotFoundException("attachment Not Found!");
        if(attachment.getLink() == null) throw new RessourceNotFoundException("No file for this attachment!");
        return fileService.getFile("files/attachments", attachment.getLink());
    }

    @Override
    public Path getAttachmentPathFile(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment == null) throw new RessourceNotFoundException("attachment Not Found!");
        if(attachment.getLink() == null) throw new RessourceNotFoundException("No file for this attachment!");
        return fileService.getPathFile("files/attachments", attachment.getLink());
    }

    @Override
    public List<AttachmentResponseDTO> getAllAttachments() {
        List<Attachment> attachments = attachmentRepository.findAll();
        return attachments.stream()
                .map(attachmentMapper::attachmentToAttachmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttachmentResponseDTO> getAllAttachmentsByRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        List<Attachment> attachments = attachmentRepository.findByRequest(request);
        return attachments.stream()
                .map(attachmentMapper::attachmentToAttachmentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AttachmentResponseDTO updateAttachment(Long attachmentId, AttachmentRequestDTO attachmentRequestDTO) {
        //Vérifier que tous les paramètres sont bien reçus
        if(attachmentRequestDTO.getName().equals("") || attachmentRequestDTO.getRequestId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Attachment attachmentLast = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachmentLast == null) throw new RessourceNotFoundException("Attachment not exist!");
        //Vérifier si le Request Id passé en paramètre existe vraiment
        Request request = requestRepository.findById(attachmentRequestDTO.getRequestId()).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        //Modifier le statut de la requête si nécessaire
        if(request.getStatus().equals("PENDING")){
            request.setStatus("SENDING");
            request.setUpdatedAt(Instant.now());
            requestRepository.save(request);
        }
        //Faire la sauvegarde
        Attachment attachment = attachmentMapper.attachmentRequestDTOToAttachment(attachmentRequestDTO);
        attachment.setId(attachmentId);
        attachment.setRequest(request);
        attachment.setCreatedAt(attachmentLast.getCreatedAt());
        attachment.setUpdatedAt(Instant.now());
        attachment.setLink(attachmentLast.getLink());
        return attachmentMapper.attachmentToAttachmentResponseDTO(attachmentRepository.save(attachment));
    }

    @Override
    public AttachmentResponseDTO updateAttachmentFile(Long attachmentId, MultipartFile file) {
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment == null) throw new RessourceNotFoundException("Ressource not exist!");
        //Sauvegarde du fichier
        String extensionFile = fileService.getFileExtension(file.getOriginalFilename());
        if(!(extensionFile.equals("pdf")))
            throw new RessourceNotFoundException("Unsupported file extension ! Good Extension : PDF.");
        String fileName = "request_"+attachment.getRequest().getId()+"_attachment_"+
                attachment.getName().replace(":", "_").replace(".", "_").replace(" ", "_")+"_"+
                new Date().getTime()+"."+extensionFile;
        if(attachment.getLink() != null) fileService.deleteFile(attachment.getLink(), "files/attachments");
        fileService.storeFile(file, fileName, "files/attachments");
        attachment.setLink(fileName);
        attachment.setUpdatedAt(Instant.now());
        return attachmentMapper.attachmentToAttachmentResponseDTO(attachmentRepository.save(attachment));
    }

    @Override
    public void deleteAttachment(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment != null){
            fileService.deleteFile(attachment.getLink(), "files/attachments");
            attachmentRepository.deleteById(attachmentId);
        }
        else throw new RessourceNotFoundException("Attachment Not Exist!");
    }
}
