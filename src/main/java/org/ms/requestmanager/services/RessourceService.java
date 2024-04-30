package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface RessourceService {
    RessourceResponseDTO saveRessource(RessourceRequestDTO ressourceRequestDTO);
    RessourceResponseDTO getRessource(Long ressourceId);
    List<RessourceResponseDTO> getAllRessources();
    List<RessourceResponseDTO> getAllRessourcesByTypeRessource(Long typeRessourceId);
    List<RessourceResponseDTO> getAllRessourcesByUe(Long ueId);
    RessourceResponseDTO updateRessource(Long ressourceId, RessourceRequestDTO ressourceRequestDTO);
    void deleteRessource(Long ressourceId);
}
