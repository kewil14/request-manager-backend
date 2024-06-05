package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface TypeRessourceService {
    TypeRessourceResponseDTO saveTypeRessource(TypeRessourceRequestDTO typeRessourceRequestDTO);
    TypeRessourceResponseDTO getTypeRessource(Long typeRessourceId);
    List<TypeRessourceResponseDTO> getAllTypeRessources();
    TypeRessourceResponseDTO updateTypeRessource(Long typeRessourceId, TypeRessourceRequestDTO typeRessourceRequestDTO);
    void deleteTypeRessource(Long typeRessourceId);
    void run(String... args) throws Exception;
}
