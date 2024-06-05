package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.TypeRessourceRequestDTO;
import org.ms.requestmanager.dto.TypeRessourceResponseDTO;
import org.ms.requestmanager.entities.TypeRessource;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.TypeRessourceMapper;
import org.ms.requestmanager.repositories.TypeRessourceRepository;
import org.ms.requestmanager.repositories.RessourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TypeRessourceServiceImpl implements TypeRessourceService, CommandLineRunner {
    private final TypeRessourceRepository typeRessourceRepository;
    private final TypeRessourceMapper typeRessourceMapper;
    private final RessourceRepository ressourceRepository;

    public TypeRessourceServiceImpl(TypeRessourceRepository typeRessourceRepository, TypeRessourceMapper typeRessourceMapper, RessourceRepository ressourceRepository) {
        this.typeRessourceRepository = typeRessourceRepository;
        this.typeRessourceMapper = typeRessourceMapper;
        this.ressourceRepository = ressourceRepository;
    }

    @Override
    public TypeRessourceResponseDTO saveTypeRessource(TypeRessourceRequestDTO typeRessourceRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(typeRessourceRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le name reçu n'existe pas déjà
        TypeRessource aC = typeRessourceRepository.findByName(typeRessourceRequestDTO.getName());
        if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        //Faire le mapping et enregistrer
        TypeRessource typeRessource = typeRessourceMapper.typeRessourceRequestDTOToTypeRessource(typeRessourceRequestDTO);
        typeRessource.setCreatedAt(Instant.now());
        return typeRessourceMapper.typeRessourceToTypeRessourceResponseDTO(typeRessourceRepository.save(typeRessource));
    }

    @Override
    public TypeRessourceResponseDTO getTypeRessource(Long typeRessourceId) {
        TypeRessource typeRessource = typeRessourceRepository.findById(typeRessourceId).orElse(null);
        if(typeRessource == null) throw new RessourceNotFoundException("TypeRessource Not Found!");
        return typeRessourceMapper.typeRessourceToTypeRessourceResponseDTO(typeRessource);
    }

    @Override
    public List<TypeRessourceResponseDTO> getAllTypeRessources() {
        List<TypeRessource> typeRessources = typeRessourceRepository.findAll();
        return typeRessources.stream()
                .map(typeRessourceMapper::typeRessourceToTypeRessourceResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TypeRessourceResponseDTO updateTypeRessource(Long typeRessourceId, TypeRessourceRequestDTO typeRessourceRequestDTO) {
        //Vérifier que tous les paramètres sont bien reçus
        if(typeRessourceRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        TypeRessource typeRessourceLast = typeRessourceRepository.findById(typeRessourceId).orElse(null);
        if(typeRessourceLast == null) throw new RessourceNotFoundException("TypeRessource not exist!");
        //S'assurer que le name n'existe pas s'il a été modifié
        if(!typeRessourceRequestDTO.getName().equals(typeRessourceLast.getName())){
            TypeRessource aC = typeRessourceRepository.findByName(typeRessourceRequestDTO.getName());
            if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        }
        //Faire la sauvegarde
        TypeRessource typeRessource = typeRessourceMapper.typeRessourceRequestDTOToTypeRessource(typeRessourceRequestDTO);
        typeRessource.setId(typeRessourceId);
        typeRessource.setCreatedAt(typeRessourceLast.getCreatedAt());
        typeRessource.setUpdatedAt(Instant.now());
        return typeRessourceMapper.typeRessourceToTypeRessourceResponseDTO(typeRessourceRepository.save(typeRessource));
    }

    @Override
    public void deleteTypeRessource(Long typeRessourceId) {
        TypeRessource typeRessource = typeRessourceRepository.findById(typeRessourceId).orElse(null);
        if(typeRessource != null){
            if(!ressourceRepository.findByTypeRessource(typeRessource).isEmpty())
                throw new RessourceNotFoundException("Error : Ressource Exist for this TypeRessource Id!");
            typeRessourceRepository.deleteById(typeRessourceId);
        }
        else throw new RessourceNotFoundException("TypeRessource Not Exist!");
    }

    @Override
    public void run(String... args) {
        if(typeRessourceRepository.findAll().isEmpty()){
            saveTypeRessource(new TypeRessourceRequestDTO("SYLLABUS"));
            saveTypeRessource(new TypeRessourceRequestDTO("LESSON"));
            saveTypeRessource(new TypeRessourceRequestDTO("TD"));
            saveTypeRessource(new TypeRessourceRequestDTO("TP"));
            saveTypeRessource(new TypeRessourceRequestDTO("SUBJECT"));
        }
    }
}
