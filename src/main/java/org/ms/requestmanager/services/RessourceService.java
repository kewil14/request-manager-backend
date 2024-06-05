package org.ms.requestmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface RessourceService {
    RessourceResponseDTO saveRessource(MultipartFile file, String ressourceIn) throws JsonProcessingException;
    RessourceResponseDTO getRessource(Long ressourceId);
    ResponseEntity<byte[]> getRessourceFile(Long ressourceId);
    Path getRessourcePathFile(Long ressourceId);
    List<RessourceResponseDTO> getAllRessources();
    List<RessourceResponseDTO> getAllRessourcesByTypeRessource(Long typeRessourceId);
    List<RessourceResponseDTO> getAllRessourcesByUe(Long ueId);
    RessourceResponseDTO updateRessource(Long ressourceId, RessourceRequestDTO ressourceRequestDTO);
    RessourceResponseDTO updateRessourceFile(Long ressourceId, MultipartFile file);
    void deleteRessource(Long ressourceId);
}