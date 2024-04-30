package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.AttachmentRequestDTO;
import org.ms.requestmanager.dto.AttachmentResponseDTO;
import org.ms.requestmanager.entities.Attachment;
import org.ms.requestmanager.entities.Request;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.AttachmentMapper;
import org.ms.requestmanager.repositories.AttachmentRepository;
import org.ms.requestmanager.repositories.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;
    private final RequestRepository requestRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper, RequestRepository requestRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
        this.requestRepository = requestRepository;
    }

    @Override
    public AttachmentResponseDTO saveAttachment(AttachmentRequestDTO attachmentRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(attachmentRequestDTO.getName().equals("") || attachmentRequestDTO.getRequestId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le Request Id passé en paramètre existe vraiment
        Request request = requestRepository.findById(attachmentRequestDTO.getRequestId()).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        //Faire le mapping et enregistrer
        Attachment attachment = attachmentMapper.attachmentRequestDTOToAttachment(attachmentRequestDTO);
        attachment.setCreatedAt(Instant.now());
        return attachmentMapper.attachmentToAttachmentResponseDTO(attachmentRepository.save(attachment));
    }

    @Override
    public AttachmentResponseDTO getAttachment(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment == null) throw new RessourceNotFoundException("Attachment Not Found!");
        return attachmentMapper.attachmentToAttachmentResponseDTO(attachment);
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
        //Faire la sauvegarde
        Attachment attachment = attachmentMapper.attachmentRequestDTOToAttachment(attachmentRequestDTO);
        attachment.setId(attachmentId);
        attachment.setCreatedAt(attachmentLast.getCreatedAt());
        attachment.setUpdatedAt(Instant.now());
        return attachmentMapper.attachmentToAttachmentResponseDTO(attachmentRepository.save(attachment));
    }

    @Override
    public void deleteAttachment(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment != null){
            attachmentRepository.deleteById(attachmentId);
        }
        else throw new RessourceNotFoundException("Attachment Not Exist!");
    }
}
