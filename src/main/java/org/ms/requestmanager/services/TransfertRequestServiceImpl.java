package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.TransfertRequestResponseDTO;
import org.ms.requestmanager.dto.TransfertRequestRequestDTO;
import org.ms.requestmanager.entities.TransfertRequest;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Request;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.TransfertRequestMapper;
import org.ms.requestmanager.repositories.TransfertRequestRepository;
import org.ms.requestmanager.repositories.PersonalRepository;
import org.ms.requestmanager.repositories.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransfertRequestServiceImpl implements TransfertRequestService {
    private final TransfertRequestRepository transfertRequestRepository;
    private final TransfertRequestMapper transfertRequestMapper;
    private final RequestRepository requestRepository;
    private final PersonalRepository personalRepository;

    public TransfertRequestServiceImpl(TransfertRequestRepository transfertRequestRepository, TransfertRequestMapper transfertRequestMapper, RequestRepository requestRepository, PersonalRepository personalRepository) {
        this.transfertRequestRepository = transfertRequestRepository;
        this.transfertRequestMapper = transfertRequestMapper;
        this.requestRepository = requestRepository;
        this.personalRepository = personalRepository;
    }

    @Override
    public TransfertRequestResponseDTO saveTransfertRequest(TransfertRequestRequestDTO transfertRequestRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(transfertRequestRequestDTO.getMessage().equals("") || transfertRequestRequestDTO.getRequestId() == null
                || transfertRequestRequestDTO.getPersonalId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le Request Id passé en paramètre existe vraiment
        Request request = requestRepository.findById(transfertRequestRequestDTO.getRequestId()).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        //Vérifier si le Personal Id passé en paramètre existe vraiment
        Personal personal = personalRepository.findById(transfertRequestRequestDTO.getPersonalId()).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
        //Faire le mapping et enregistrer
        TransfertRequest transfertRequest = transfertRequestMapper.transfertRequestRequestDTOToTransfertRequest(transfertRequestRequestDTO);
        transfertRequest.setCreatedAt(Instant.now());
        transfertRequest.setRequest(request);
        transfertRequest.setPersonal(personal);
        return transfertRequestMapper.transfertRequestToTransfertRequestResponseDTO(transfertRequestRepository.save(transfertRequest));
    }

    @Override
    public TransfertRequestResponseDTO getTransfertRequest(Long transfertRequestId) {
        TransfertRequest transfertRequest = transfertRequestRepository.findById(transfertRequestId).orElse(null);
        if(transfertRequest == null) throw new RessourceNotFoundException("TransfertRequest Not Found!");
        return transfertRequestMapper.transfertRequestToTransfertRequestResponseDTO(transfertRequest);
    }

    @Override
    public List<TransfertRequestResponseDTO> getAllTransfertRequests() {
        List<TransfertRequest> transfertRequests = transfertRequestRepository.findAll();
        return transfertRequests.stream()
                .map(transfertRequestMapper::transfertRequestToTransfertRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransfertRequestResponseDTO> getAllTransfertRequestsByRequest(Long requestId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        List<TransfertRequest> transfertRequests = transfertRequestRepository.findByRequest(request);
        return transfertRequests.stream()
                .map(transfertRequestMapper::transfertRequestToTransfertRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransfertRequestResponseDTO> getAllTransfertRequestsByRequestAndPersonal(Long requestId, Long personalId) {
        Request request = requestRepository.findById(requestId).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this Personal Id!");
        List<TransfertRequest> transfertRequests = transfertRequestRepository.findByRequestAndPersonal(request, personal);
        return transfertRequests.stream()
                .map(transfertRequestMapper::transfertRequestToTransfertRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransfertRequestResponseDTO> getAllTransfertRequestsByPersonal(Long personalId) {
        Personal personal = personalRepository.findById(personalId).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this Personal Id!");
        List<TransfertRequest> transfertRequests = transfertRequestRepository.findByPersonal(personal);
        return transfertRequests.stream()
                .map(transfertRequestMapper::transfertRequestToTransfertRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransfertRequestResponseDTO updateTransfertRequest(Long transfertRequestId, TransfertRequestRequestDTO transfertRequestRequestDTO) {
        //Vérifier que tous les paramètres sont bien reçus
        if(transfertRequestRequestDTO.getMessage().equals("") || transfertRequestRequestDTO.getRequestId() == null
                || transfertRequestRequestDTO.getPersonalId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        TransfertRequest transfertRequestLast = transfertRequestRepository.findById(transfertRequestId).orElse(null);
        if(transfertRequestLast == null) throw new RessourceNotFoundException("TransfertRequest not exist!");
        //Vérifier si le Request Id passé en paramètre existe vraiment
        Request request = requestRepository.findById(transfertRequestRequestDTO.getRequestId()).orElse(null);
        if(request == null) throw new RessourceNotFoundException("Request Not Found for this requestId!");
        //Vérifier si le Personal Id passé en paramètre existe vraiment
        Personal personal = personalRepository.findById(transfertRequestRequestDTO.getPersonalId()).orElse(null);
        if(personal == null) throw new RessourceNotFoundException("Personal Not Found for this personalId!");
        //Faire la sauvegarde
        TransfertRequest transfertRequest = transfertRequestMapper.transfertRequestRequestDTOToTransfertRequest(transfertRequestRequestDTO);
        transfertRequest.setId(transfertRequestId);
        transfertRequest.setRequest(request);
        transfertRequest.setPersonal(personal);
        transfertRequest.setCreatedAt(transfertRequestLast.getCreatedAt());
        transfertRequest.setUpdatedAt(Instant.now());
        return transfertRequestMapper.transfertRequestToTransfertRequestResponseDTO(transfertRequestRepository.save(transfertRequest));
    }

    @Override
    public void deleteTransfertRequest(Long transfertRequestId) {
        TransfertRequest transfertRequest = transfertRequestRepository.findById(transfertRequestId).orElse(null);
        if(transfertRequest != null){
            transfertRequestRepository.deleteById(transfertRequestId);
        }
        else throw new RessourceNotFoundException("TransfertRequest Not Exist!");
    }
}
