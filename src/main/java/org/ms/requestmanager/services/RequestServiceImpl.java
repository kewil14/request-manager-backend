package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.RequestRequestDTO;
import org.ms.requestmanager.dto.RequestResponseDTO;
import org.ms.requestmanager.entities.*;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.RequestMapper;
import org.ms.requestmanager.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final TypeRequestRepository typeRequestRepository;
    private final StudentRepository studentRepository;
    private final TypePersonalRepository typePersonalRepository;
    private final PersonalRepository personalRepository;
    private final UeRepository ueRepository;
    private final AttachmentRepository attachmentRepository;
    private final CommentRequestRepository commentRequestRepository;
    private final TransfertRequestRepository transfertRequestRepository;

    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper, TypeRequestRepository typeRequestRepository, StudentRepository studentRepository, TypePersonalRepository typePersonalRepository, PersonalRepository personalRepository, UeRepository ueRepository, AttachmentRepository attachmentRepository, CommentRequestRepository commentRequestRepository, TransfertRequestRepository transfertRequestRepository) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.typeRequestRepository = typeRequestRepository;
        this.studentRepository = studentRepository;
        this.typePersonalRepository = typePersonalRepository;
        this.personalRepository = personalRepository;
        this.ueRepository = ueRepository;
        this.attachmentRepository = attachmentRepository;
        this.commentRequestRepository = commentRequestRepository;
        this.transfertRequestRepository = transfertRequestRepository;
    }

    private Personal getRecipientRequest(RequestRequestDTO requestRequestDTO, TypeRequest typeRequest, Student student, Ue ue) {
        TypePersonal typePersonal;
        Personal personal = null;
        switch (typeRequest.getName()) {
            case "IDENTIFICATION": {
                typePersonal = typePersonalRepository.findByName("CHIEF_SCHOOLING");
                List<Personal> personals = personalRepository.findByTypePersonal(typePersonal);
                personal = personals.get(0);
                break;
            }
            case "NOTE_CC":
            case "AUTORISATION_ABSENCE":
                if (ue == null)
                    throw new RessourceNotFoundException("Data required for UE not received.");
                personal = personalRepository.findById(ue.getPersonal().getId()).orElse(null);
                break;
            case "NOTE_EE":
            case "AUTORISATION_EVALUATION":
                if (ue == null)
                    throw new RessourceNotFoundException("Data required for UE not received.");
                personal = personalRepository.findById(ue.getLevel().getPersonal().getId()).orElse(null);
                break;
            case "REQUEST_ACADEMIC_TRANSCRIPT":
            case "REQUEST_DIPLOMA": {
                typePersonal = typePersonalRepository.findByName("DIRECTOR");
                List<Personal> personals = personalRepository.findByTypePersonal(typePersonal);
                personal = personals.get(0);
                break;
            }
            case "OTHER":
                if (requestRequestDTO.getObjet().equals("") || requestRequestDTO.getObjet()==null)
                    throw new RessourceNotFoundException("Data required for OBJECT not received.");
                personal = personalRepository.findById(
                        student.getLevel().getDepartment().getPersonal().getId()
                ).orElse(null);
                break;
        }
        return personal;
    }

    @Override
    public RequestResponseDTO saveRequest(RequestRequestDTO requestRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(requestRequestDTO.getMessage().equals("") || requestRequestDTO.getMessage() == null ||
                requestRequestDTO.getTypeRequestId() == null || requestRequestDTO.getStudentId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Créer l'objet qui sera enregistré
        Request request = requestMapper.requestRequestDTOToRequest(requestRequestDTO);
        //Vérifier si le TypeRequest Id passé en paramètre existe vraiment
        TypeRequest typeRequest = typeRequestRepository.findById(requestRequestDTO.getTypeRequestId()).orElse(null);
        if(typeRequest == null) throw new RessourceNotFoundException("TypeRequest Not Found for this typeRequestId!");
        //Vérifier si le Student Id passé en paramètre existe vraiment
        Student student = studentRepository.findById(requestRequestDTO.getStudentId()).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student Not Found for this studentId!");
        //Vérifier si le Ue Id passé en paramètre existe vraiment
        Ue ue = null;
        if(requestRequestDTO.getUeId()!=null){
            ue = ueRepository.findById(requestRequestDTO.getUeId()).orElse(null);
            if(ue == null) throw new RessourceNotFoundException("Ue Not Found for this ueId!");
        }
        //Define Recipient of Request
        Personal personal = getRecipientRequest(requestRequestDTO, typeRequest, student, ue);
        //Enregistrer
        request.setCreatedAt(Instant.now());
        request.setStatus("SENDING");
        request.setState(1L);
        request.setTypeRequest(typeRequest);
        request.setStudent(student);
        request.setUe(ue);
        request.setPersonal(personal);
        return requestMapper.requestToRequestResponseDTO(requestRepository.save(request));
    }

    @Override
    public RequestResponseDTO getRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found!");
        return requestMapper.requestToRequestResponseDTO(request);
    }

    @Override
    public List<RequestResponseDTO> getAllRequests() {
        List<Request> requests = requestRepository.findByState(1L);
        return requests.stream()
                .map(requestMapper::requestToRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDTO> getAllRequestsByTypeRequest(Long typeRequestId) {
        TypeRequest typeRequest = typeRequestRepository.findById(typeRequestId).orElse(null);
        if(typeRequest == null) throw new RessourceNotFoundException("TypeRequest Not Found for this typeRequestId!");
        List<Request> requests = requestRepository.findByTypeRequestAndState(typeRequest,1L);
        return requests.stream()
                .map(requestMapper::requestToRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDTO> getAllRequestsByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student Not Found for this studentId!");
        List<Request> requests = requestRepository.findByStudentAndState(student, 1L);
        return requests.stream()
                .map(requestMapper::requestToRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDTO> getAllRequestsByPersonal(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
        List<Request> requests = requestRepository.findByPersonalAndState(personal, 1L);
        return requests.stream()
                .peek(request -> {
                    if (request.getStatus().equals("SENDING")) {
                        request.setStatus("PENDING");
                    }
                })
                .map(requestMapper::requestToRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestResponseDTO> getAllRequestsByUe(Long ueId) {
        Ue ue = ueRepository.findById(ueId).orElse(null);
        if(ue == null) throw new RessourceNotFoundException("Ue Not Found for this ueId!");
        List<Request> requests = requestRepository.findByUeAndState(ue, 1L);
        return requests.stream()
                .map(requestMapper::requestToRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RequestResponseDTO updateRequest(Long requestId, RequestRequestDTO requestRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(requestRequestDTO.getMessage().equals("") || requestRequestDTO.getTypeRequestId() == null
                || requestRequestDTO.getStudentId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Créer l'objet qui sera enregistré
        Request request = requestMapper.requestRequestDTOToRequest(requestRequestDTO);
        //Vérifier si le TypeRequest Id passé en paramètre existe vraiment
        TypeRequest typeRequest = typeRequestRepository.findById(requestRequestDTO.getTypeRequestId()).orElse(null);
        if(typeRequest == null) throw new RessourceNotFoundException("TypeRequest Not Found for this typeRequestId!");
        //Vérifier si le Student Id passé en paramètre existe vraiment
        Student student = studentRepository.findById(requestRequestDTO.getStudentId()).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student Not Found for this studentId!");
        //Vérifier si le Ue Id passé en paramètre existe vraiment
        Ue ue = null;
        if(requestRequestDTO.getUeId()!=null){
            ue = ueRepository.findById(requestRequestDTO.getUeId()).orElse(null);
            if(ue == null) throw new RessourceNotFoundException("Ue Not Found for this ueId!");
        }
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Request requestLast = requestRepository.findById(requestId).orElse(null);
        if(requestLast == null) throw new RessourceNotFoundException("Request not exist!");
        //Define Recipient of Request
        Personal personal = getRecipientRequest(requestRequestDTO, typeRequest, student, ue);
        //Enregistrer
        request.setId(requestId);
        request.setCreatedAt(requestLast.getCreatedAt());
        request.setStatus(requestLast.getStatus());
        request.setState(1L);
        request.setTypeRequest(typeRequest);
        request.setStudent(student);
        request.setUe(ue);
        request.setPersonal(personal);
        request.setUpdatedAt(Instant.now());
        return requestMapper.requestToRequestResponseDTO(requestRepository.save(request));
    }

    @Override
    public void archiveRequest(Long requestId) {
        if(requestRepository.findById(requestId).isPresent())
            requestRepository.findById(requestId).ifPresent(accountingSubClass -> {
                accountingSubClass.setState(0L);
                accountingSubClass.setUpdatedAt(Instant.now());
                requestRepository.save(accountingSubClass);
            });
        else throw new RessourceNotFoundException("Request Not Exist!");
    }

    @Override
    public void updateStatusRequest(Long requestId, String status) {
        if(requestRepository.findById(requestId).isPresent())
            requestRepository.findById(requestId).ifPresent(request -> {
                request.setStatus(status);
                request.setUpdatedAt(Instant.now());
                requestRepository.save(request);
            });
        else throw new RessourceNotFoundException("Request Not Exist!");
    }

    @Override
    public void deleteRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request != null){
            if(!attachmentRepository.findByRequest(request).isEmpty())
                throw new RessourceNotFoundException("Error : Attachment Exist for this Request Id!");
            if(!commentRequestRepository.findByRequest(request).isEmpty())
                throw new RessourceNotFoundException("Error : CommentRequest Exist for this Request Id!");
            if(!transfertRequestRepository.findByRequest(request).isEmpty())
                throw new RessourceNotFoundException("Error : Transfert of Request Exist for this Request Id!");
            requestRepository.deleteById(requestId);
        }
        else throw new RessourceNotFoundException("Request Not Exist!");
    }
}
