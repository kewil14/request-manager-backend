package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.TypeRequestRequestDTO;
import org.ms.requestmanager.dto.TypeRequestResponseDTO;
import org.ms.requestmanager.entities.TypeRequest;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.TypeRequestMapper;
import org.ms.requestmanager.repositories.TypeRequestRepository;
import org.ms.requestmanager.repositories.RequestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TypeRequestServiceImpl implements TypeRequestService, CommandLineRunner {
    private final TypeRequestRepository typeRequestRepository;
    private final TypeRequestMapper typeRequestMapper;
    private final RequestRepository requestRepository;

    public TypeRequestServiceImpl(TypeRequestRepository typeRequestRepository, TypeRequestMapper typeRequestMapper, RequestRepository requestRepository) {
        this.typeRequestRepository = typeRequestRepository;
        this.typeRequestMapper = typeRequestMapper;
        this.requestRepository = requestRepository;
    }

    @Override
    public TypeRequestResponseDTO saveTypeRequest(TypeRequestRequestDTO typeRequestRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(typeRequestRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le name reçu n'existe pas déjà
        TypeRequest aC = typeRequestRepository.findByName(typeRequestRequestDTO.getName());
        if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        //Faire le mapping et enregistrer
        TypeRequest typeRequest = typeRequestMapper.typeRequestRequestDTOToTypeRequest(typeRequestRequestDTO);
        typeRequest.setCreatedAt(Instant.now());
        return typeRequestMapper.typeRequestToTypeRequestResponseDTO(typeRequestRepository.save(typeRequest));
    }

    @Override
    public TypeRequestResponseDTO getTypeRequest(Long typeRequestId) {
        TypeRequest typeRequest = typeRequestRepository.findById(typeRequestId).orElse(null);
        if(typeRequest == null) throw new RessourceNotFoundException("TypeRequest Not Found!");
        return typeRequestMapper.typeRequestToTypeRequestResponseDTO(typeRequest);
    }

    @Override
    public List<TypeRequestResponseDTO> getAllTypeRequests() {
        List<TypeRequest> typeRequests = typeRequestRepository.findAll();
        return typeRequests.stream()
                .map(typeRequestMapper::typeRequestToTypeRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TypeRequestResponseDTO updateTypeRequest(Long typeRequestId, TypeRequestRequestDTO typeRequestRequestDTO) {
        //Vérifier que tous les paramètres sont bien reçus
        if(typeRequestRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        TypeRequest typeRequestLast = typeRequestRepository.findById(typeRequestId).orElse(null);
        if(typeRequestLast == null) throw new RessourceNotFoundException("TypeRequest not exist!");
        //S'assurer que le name n'existe pas s'il a été modifié
        if(!typeRequestRequestDTO.getName().equals(typeRequestLast.getName())){
            TypeRequest aC = typeRequestRepository.findByName(typeRequestRequestDTO.getName());
            if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        }
        //Faire la sauvegarde
        TypeRequest typeRequest = typeRequestMapper.typeRequestRequestDTOToTypeRequest(typeRequestRequestDTO);
        typeRequest.setId(typeRequestId);
        typeRequest.setCreatedAt(typeRequestLast.getCreatedAt());
        typeRequest.setUpdatedAt(Instant.now());
        return typeRequestMapper.typeRequestToTypeRequestResponseDTO(typeRequestRepository.save(typeRequest));
    }

    @Override
    public void deleteTypeRequest(Long typeRequestId) {
        TypeRequest typeRequest = typeRequestRepository.findById(typeRequestId).orElse(null);
        if(typeRequest != null){
            if(!requestRepository.findByTypeRequest(typeRequest).isEmpty())
                throw new RessourceNotFoundException("Error : Request Exist for this TypeRequest Id!");
            typeRequestRepository.deleteById(typeRequestId);
        }
        else throw new RessourceNotFoundException("TypeRequest Not Exist!");
    }

    @Override
    public void run(String... args) {
        if(typeRequestRepository.findAll().isEmpty()){
            saveTypeRequest(new TypeRequestRequestDTO("IDENTIFICATION"));
            saveTypeRequest(new TypeRequestRequestDTO("NOTE_CC"));
            saveTypeRequest(new TypeRequestRequestDTO("NOTE_EE"));
            saveTypeRequest(new TypeRequestRequestDTO("AUTORISATION_ABSENCE"));
            saveTypeRequest(new TypeRequestRequestDTO("AUTORISATION_EVALUATION"));
            saveTypeRequest(new TypeRequestRequestDTO("REQUEST_ACADEMIC_TRANSCRIPT"));
            saveTypeRequest(new TypeRequestRequestDTO("REQUEST_DIPLOMA"));
            saveTypeRequest(new TypeRequestRequestDTO("OTHER"));
        }
    }
}
