package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.TypePersonalRequestDTO;
import org.ms.requestmanager.dto.TypePersonalResponseDTO;
import org.ms.requestmanager.entities.TypePersonal;
import org.ms.requestmanager.exceptions.RessourceNotFoundException;
import org.ms.requestmanager.mappers.TypePersonalMapper;
import org.ms.requestmanager.repositories.TypePersonalRepository;
import org.ms.requestmanager.repositories.PersonalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TypePersonalServiceImpl implements TypePersonalService, CommandLineRunner {
    private final TypePersonalRepository typePersonalRepository;
    private final TypePersonalMapper typePersonalMapper;
    private final PersonalRepository personalRepository;

    public TypePersonalServiceImpl(TypePersonalRepository typePersonalRepository, TypePersonalMapper typePersonalMapper, PersonalRepository personalRepository) {
        this.typePersonalRepository = typePersonalRepository;
        this.typePersonalMapper = typePersonalMapper;
        this.personalRepository = personalRepository;
    }

    @Override
    public TypePersonalResponseDTO saveTypePersonal(TypePersonalRequestDTO typePersonalRequestDTO) {
        //Vérifier que tous les paramètres ont été reçus
        if(typePersonalRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si le name reçu n'existe pas déjà
        TypePersonal aC = typePersonalRepository.findByName(typePersonalRequestDTO.getName());
        if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        //Faire le mapping et enregistrer
        TypePersonal typePersonal = typePersonalMapper.typePersonalRequestDTOToTypePersonal(typePersonalRequestDTO);
        typePersonal.setCreatedAt(Instant.now());
        return typePersonalMapper.typePersonalToTypePersonalResponseDTO(typePersonalRepository.save(typePersonal));
    }

    @Override
    public TypePersonalResponseDTO getTypePersonal(Long typePersonalId) {
        TypePersonal typePersonal = typePersonalRepository.findById(typePersonalId).orElse(null);
        if(typePersonal == null) throw new RessourceNotFoundException("TypePersonal Not Found!");
        return typePersonalMapper.typePersonalToTypePersonalResponseDTO(typePersonal);
    }

    @Override
    public List<TypePersonalResponseDTO> getAllTypePersonals() {
        List<TypePersonal> typePersonals = typePersonalRepository.findAll();
        return typePersonals.stream()
                .map(typePersonalMapper::typePersonalToTypePersonalResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TypePersonalResponseDTO updateTypePersonal(Long typePersonalId, TypePersonalRequestDTO typePersonalRequestDTO) {
        //Vérifier que tous les paramètres sont bien reçus
        if(typePersonalRequestDTO.getName().equals(""))
            throw new RessourceNotFoundException("Data required not received.");
        //Vérifier si l'élément à modifier existe déjà à partir de l'id
        TypePersonal typePersonalLast = typePersonalRepository.findById(typePersonalId).orElse(null);
        if(typePersonalLast == null) throw new RessourceNotFoundException("TypePersonal not exist!");
        //S'assurer que le name n'existe pas s'il a été modifié
        if(!typePersonalRequestDTO.getName().equals(typePersonalLast.getName())){
            TypePersonal aC = typePersonalRepository.findByName(typePersonalRequestDTO.getName());
            if(aC!=null) throw new RessourceNotFoundException("Name already exist");
        }
        //Faire la sauvegarde
        TypePersonal typePersonal = typePersonalMapper.typePersonalRequestDTOToTypePersonal(typePersonalRequestDTO);
        typePersonal.setId(typePersonalId);
        typePersonal.setCreatedAt(typePersonalLast.getCreatedAt());
        typePersonal.setUpdatedAt(Instant.now());
        return typePersonalMapper.typePersonalToTypePersonalResponseDTO(typePersonalRepository.save(typePersonal));
    }

    @Override
    public void deleteTypePersonal(Long typePersonalId) {
        TypePersonal typePersonal = typePersonalRepository.findById(typePersonalId).orElse(null);
        if(typePersonal != null){
            if(!personalRepository.findByTypePersonal(typePersonal).isEmpty())
                throw new RessourceNotFoundException("Error : Personal Exist for this TypePersonal Id!");
            typePersonalRepository.deleteById(typePersonalId);
        }
        else throw new RessourceNotFoundException("TypePersonal Not Exist!");
    }

    @Override
    public void run(String... args) {
        if(typePersonalRepository.findAll().isEmpty()){
            saveTypePersonal(new TypePersonalRequestDTO("TEACHER"));
            saveTypePersonal(new TypePersonalRequestDTO("DIRECTOR"));
            saveTypePersonal(new TypePersonalRequestDTO("SECRETARY"));
            saveTypePersonal(new TypePersonalRequestDTO("CHIEF_SCHOOLING"));
            saveTypePersonal(new TypePersonalRequestDTO("CHIEF_STAGE"));
        }
    }
}
