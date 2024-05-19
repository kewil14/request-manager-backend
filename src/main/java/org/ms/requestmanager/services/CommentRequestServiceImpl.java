package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.CommentRequestResponseDTO;
import org.ms.requestmanager.dto.CommentRequestRequestDTO;
import org.ms.requestmanager.entities.*;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.CommentRequestMapper;
import org.ms.requestmanager.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentRequestServiceImpl implements CommentRequestService {
    private final CommentRequestRepository commentRequestRepository;
    private final CommentRequestMapper commentRequestMapper;
    private final RequestRepository requestRepository;
    private final PersonalRepository personalRepository;
    private final TransfertRequestRepository transfertRequestRepository;
    private final StudentRepository studentRepository;

    public CommentRequestServiceImpl(CommentRequestRepository commentRequestRepository, CommentRequestMapper commentRequestMapper, RequestRepository requestRepository, PersonalRepository personalRepository, TransfertRequestRepository transfertRequestRepository, StudentRepository studentRepository) {
        this.commentRequestRepository = commentRequestRepository;
        this.commentRequestMapper = commentRequestMapper;
        this.requestRepository = requestRepository;
        this.personalRepository = personalRepository;
        this.transfertRequestRepository = transfertRequestRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public CommentRequestResponseDTO saveCommentRequest(CommentRequestRequestDTO commentRequestRequestDTO, int mode) {
        //Vérifier que tous les paramètres ont été reçus
        if(commentRequestRequestDTO.getMessage().equals("") || commentRequestRequestDTO.getRequestId() == null
                || (commentRequestRequestDTO.getPersonalId() == null && commentRequestRequestDTO.getStudentId() == null))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le Request Id passé en paramètre existe vraiment
        Request request = requestRepository.findById(commentRequestRequestDTO.getRequestId()).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        //Faire le mapping et enregistrer
        CommentRequest commentRequest = commentRequestMapper.commentRequestRequestDTOToCommentRequest(commentRequestRequestDTO);
        if(mode == 1){
            //Vérifier si le Personal Id passé en paramètre existe vraiment
            Personal personal = personalRepository.findById(commentRequestRequestDTO.getPersonalId()).orElse(null);
            if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
            //Vérifier que le Personal qui commente est autorisé à le faire : soit c'est le destinataire de la requête, soit c'est une personne à qui la requête a été transféré
            List<TransfertRequest> transfertRequests = transfertRequestRepository.findByRequest(request);
            List<Personal> personalsCanComment = transfertRequests.stream()
                    .map(TransfertRequest::getPersonal)
                    .collect(Collectors.toList());
            personalsCanComment.add(request.getPersonal());
            if(!personalsCanComment.contains(personal)) throw new RessourceNotFoundException("This Personal can't do a comment");
            commentRequest.setPersonal(personal);
        } else{
            //Vérifier si le Student Id passé en paramètre existe vraiment
            Student student = studentRepository.findById(commentRequestRequestDTO.getStudentId()).orElse(null);
            if(student == null) throw new RessourceNotFoundException("Student Not Found for this studentId!");
            //Vérifier que le Student qui commente est autorisé à le faire : l'émetteur de la requête
            if(!request.getStudent().equals(student)) throw new RessourceNotFoundException("This Student can't do a comment");
            commentRequest.setStudent(student);
        }
        commentRequest.setCreatedAt(Instant.now());
        commentRequest.setRequest(request);
        return commentRequestMapper.commentRequestToCommentRequestResponseDTO(commentRequestRepository.save(commentRequest));
    }

    @Override
    public CommentRequestResponseDTO getCommentRequest(Long commentRequestId) {
        CommentRequest commentRequest = commentRequestRepository.findById(commentRequestId).orElse(null);
        if(commentRequest == null) throw new RessourceNotFoundException("CommentRequest Not Found!");
        return commentRequestMapper.commentRequestToCommentRequestResponseDTO(commentRequest);
    }

    @Override
    public List<CommentRequestResponseDTO> getAllCommentRequests() {
        List<CommentRequest> commentRequests = commentRequestRepository.findAll();
        return commentRequests.stream()
                .map(commentRequestMapper::commentRequestToCommentRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentRequestResponseDTO> getAllCommentRequestsByRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        List<CommentRequest> commentRequests = commentRequestRepository.findByRequest(request);
        return commentRequests.stream()
                .map(commentRequestMapper::commentRequestToCommentRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentRequestResponseDTO> getAllCommentRequestsByPersonal(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this Personal Id!");
        List<CommentRequest> commentRequests = commentRequestRepository.findByPersonal(personal);
        return commentRequests.stream()
                .map(commentRequestMapper::commentRequestToCommentRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentRequestResponseDTO> getAllCommentRequestsByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student Not Found for this Student Id!");
        List<CommentRequest> commentRequests = commentRequestRepository.findByStudent(student);
        return commentRequests.stream()
                .map(commentRequestMapper::commentRequestToCommentRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentRequestResponseDTO> getAllCommentRequestsByRequestAndPersonal(Long requestId, Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this Personal Id!");
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        List<CommentRequest> commentRequests = commentRequestRepository.findByRequestAndPersonal(request, personal);
        return commentRequests.stream()
                .map(commentRequestMapper::commentRequestToCommentRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentRequestResponseDTO> getAllCommentRequestsByRequestAndStudent(Long requestId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null) throw new RessourceNotFoundException("Student Not Found for this Student Id!");
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        List<CommentRequest> commentRequests = commentRequestRepository.findByRequestAndStudent(request, student);
        return commentRequests.stream()
                .map(commentRequestMapper::commentRequestToCommentRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentRequestResponseDTO updateCommentRequest(Long commentRequestId, CommentRequestRequestDTO commentRequestRequestDTO, int mode) {
        //Vérifier que tous les paramètres sont bien reçus
        if(commentRequestRequestDTO.getMessage().equals("") || commentRequestRequestDTO.getRequestId() == null
                || (commentRequestRequestDTO.getPersonalId() == null && commentRequestRequestDTO.getStudentId() == null))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        CommentRequest commentRequestLast = commentRequestRepository.findById(commentRequestId).orElse(null);
        if(commentRequestLast == null) throw new RessourceNotFoundException("CommentRequest not exist!");
        //Vérifier si le Request Id passé en paramètre existe vraiment
        Request request = requestRepository.findById(commentRequestRequestDTO.getRequestId()).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        //Faire le mapping et enregistrer
        CommentRequest commentRequest = commentRequestMapper.commentRequestRequestDTOToCommentRequest(commentRequestRequestDTO);
        if(mode == 1){
            //Vérifier si le Personal Id passé en paramètre existe vraiment
            Personal personal = personalRepository.findById(commentRequestRequestDTO.getPersonalId()).orElse(null);
            if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
            //Vérifier que le Personal qui commente est autorisé à le faire : soit c'est le destinataire de la requête, soit c'est une personne à qui la requête a été transféré
            List<TransfertRequest> transfertRequests = transfertRequestRepository.findByRequest(request);
            List<Personal> personalsCanComment = transfertRequests.stream()
                    .map(TransfertRequest::getPersonal)
                    .collect(Collectors.toList());
            personalsCanComment.add(request.getPersonal());
            if(!personalsCanComment.contains(personal)) throw new RessourceNotFoundException("This Personal can't do a comment");
            commentRequest.setPersonal(personal);
        } else{
            //Vérifier si le Student Id passé en paramètre existe vraiment
            Student student = studentRepository.findById(commentRequestRequestDTO.getStudentId()).orElse(null);
            if(student == null) throw new RessourceNotFoundException("Student Not Found for this studentId!");
            //Vérifier que le Student qui commente est autorisé à le faire : l'émetteur de la requête
            if(!request.getStudent().equals(student)) throw new RessourceNotFoundException("This Student can't do a comment");
            commentRequest.setStudent(student);
        }
        commentRequest.setId(commentRequestId);
        commentRequest.setRequest(request);
        commentRequest.setCreatedAt(commentRequestLast.getCreatedAt());
        commentRequest.setUpdatedAt(Instant.now());
        return commentRequestMapper.commentRequestToCommentRequestResponseDTO(commentRequestRepository.save(commentRequest));
    }

    @Override
    public void deleteCommentRequest(Long commentRequestId) {
        CommentRequest commentRequest = commentRequestRepository.findById(commentRequestId).orElse(null);
        if(commentRequest != null){
            commentRequestRepository.deleteById(commentRequestId);
        }
        else throw new RessourceNotFoundException("CommentRequest Not Exist!");
    }
}
