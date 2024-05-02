package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.RessourceRequestDTO;
import org.ms.requestmanager.dto.RessourceResponseDTO;
import org.ms.requestmanager.entities.Ressource;
import org.ms.requestmanager.entities.TypeRessource;
import org.ms.requestmanager.entities.Ue;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.RessourceMapper;
import org.ms.requestmanager.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RessourceServiceImpl implements RessourceService {
    private final RessourceRepository ressourceRepository;
    private final RessourceMapper ressourceMapper;
    private final TypeRessourceRepository typeRessourceRepository;
    private final UeRepository ueRepository;

    public RessourceServiceImpl(RessourceRepository ressourceRepository, RessourceMapper ressourceMapper, TypeRessourceRepository typeRessourceRepository, UeRepository ueRepository) {
        this.ressourceRepository = ressourceRepository;
        this.ressourceMapper = ressourceMapper;
        this.typeRessourceRepository = typeRessourceRepository;
        this.ueRepository = ueRepository;
    }

    @Override
    public RessourceResponseDTO saveRessource(RessourceRequestDTO ressourceRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(ressourceRequestDTO.getName().equals("") || ressourceRequestDTO.getTypeRessourceId() == null || ressourceRequestDTO.getUeId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le TypeRessource Id passé en paramètre existe vraiment
        TypeRessource typeRessource = typeRessourceRepository.findById(ressourceRequestDTO.getTypeRessourceId()).orElse(null);
        if(typeRessource == null) throw new RessourceNotFoundException("TypeRessource Not Found for this typeRessourceId!");
        //Vérifier si le Department Id passé en paramètre existe vraiment
        Ue ue = ueRepository.findById(ressourceRequestDTO.getUeId()).orElse(null);
        if(ue == null) throw new RessourceNotFoundException("Ue Not Found for this ueId!");
        //Faire le mapping et enregistrer
        Ressource ressource = ressourceMapper.ressourceRequestDTOToRessource(ressourceRequestDTO);
        ressource.setCreatedAt(Instant.now());
        ressource.setTypeRessource(typeRessource);
        return ressourceMapper.ressourceToRessourceResponseDTO(ressourceRepository.save(ressource));
    }

    @Override
    public RessourceResponseDTO getRessource(Long ressourceId) {
        Ressource ressource = ressourceRepository.findById(ressourceId).orElse(null);
        if(ressource == null) throw new RessourceNotFoundException("Ressource Not Found!");
        return ressourceMapper.ressourceToRessourceResponseDTO(ressource);
    }

    @Override
    public List<RessourceResponseDTO> getAllRessources() {
        List<Ressource> ressources = ressourceRepository.findAll();
        return ressources.stream()
                .map(ressourceMapper::ressourceToRessourceResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RessourceResponseDTO> getAllRessourcesByTypeRessource(Long typeRessourceId) {
        TypeRessource typeRessource = typeRessourceRepository.findById(typeRessourceId).orElse(null);
        if(typeRessource == null) throw new RessourceNotFoundException("TypeRessource Not Found for this typeRessourceId!");
        List<Ressource> ressources = ressourceRepository.findByTypeRessource(typeRessource);
        return ressources.stream()
                .map(ressourceMapper::ressourceToRessourceResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RessourceResponseDTO> getAllRessourcesByUe(Long ueId) {
        Ue ue = ueRepository.findById(ueId).orElse(null);
        if(ue == null) throw new RessourceNotFoundException("Ue Not Found for this ueId!");
        List<Ressource> ressources = ressourceRepository.findByUe(ue);
        return ressources.stream()
                .map(ressourceMapper::ressourceToRessourceResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RessourceResponseDTO updateRessource(Long ressourceId, RessourceRequestDTO ressourceRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(ressourceRequestDTO.getName().equals("") || ressourceRequestDTO.getTypeRessourceId() == null || ressourceRequestDTO.getUeId() == null)
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le TypeRessource Id passé en paramètre existe vraiment
        TypeRessource typeRessource = typeRessourceRepository.findById(ressourceRequestDTO.getTypeRessourceId()).orElse(null);
        if(typeRessource == null) throw new RessourceNotFoundException("TypeRessource Not Found for this typeRessourceId!");
        //Vérifier si le Department Id passé en paramètre existe vraiment
        Ue ue = ueRepository.findById(ressourceRequestDTO.getUeId()).orElse(null);
        if(ue == null) throw new RessourceNotFoundException("Ue Not Found for this ueId!");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        Ressource ressourceLast = ressourceRepository.findById(ressourceId).orElse(null);
        if(ressourceLast == null) throw new RessourceNotFoundException("Ressource not exist!");
        //Faire la sauvegarde
        Ressource ressource = ressourceMapper.ressourceRequestDTOToRessource(ressourceRequestDTO);
        ressource.setId(ressourceId);
        ressource.setTypeRessource(typeRessource);
        ressource.setCreatedAt(ressourceLast.getCreatedAt());
        ressource.setUpdatedAt(Instant.now());
        return ressourceMapper.ressourceToRessourceResponseDTO(ressourceRepository.save(ressource));
    }

    @Override
    public void deleteRessource(Long ressourceId) {
        Ressource ressource = ressourceRepository.findById(ressourceId).orElse(null);
        if(ressource != null){
            ressourceRepository.deleteById(ressourceId);
        }
        else throw new RessourceNotFoundException("Ressource Not Exist!");
    }
}
