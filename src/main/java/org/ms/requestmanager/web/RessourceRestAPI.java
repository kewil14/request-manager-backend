package org.ms.requestmanager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.RessourceRequestDTO;
import org.ms.requestmanager.dto.RessourceResponseDTO;
import org.ms.requestmanager.services.RessourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class RessourceRestAPI {
    private final RessourceService ressourceService;

    public RessourceRestAPI(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }
    @PostMapping(path = "/ressources")
    public RessourceResponseDTO save(@RequestParam("file") MultipartFile file, String ressourceIn) throws JsonProcessingException {
        return ressourceService.saveRessource(file, ressourceIn);
    }

    @GetMapping(path = "/ressources/{id}")
    public RessourceResponseDTO getRessource(@PathVariable Long id){
        return ressourceService.getRessource(id);
    }

    @GetMapping(path = "/ressources/file/{id}")
    public ResponseEntity<byte[]> getRessourceFile(@PathVariable Long id) {
        return ressourceService.getRessourceFile(id);
    }

    @GetMapping(path = "/ressources/pathfile/{id}")
    public Path getRessourcePathFile(@PathVariable Long id) {
        return ressourceService.getRessourcePathFile(id);
    }

    @GetMapping(path = "/ressources")
    public List<RessourceResponseDTO> allRessources(){
        return ressourceService.getAllRessources();
    }
    @GetMapping(path = "/ressources/typeRessource/{typeRessourceId}")
    public List<RessourceResponseDTO> allRessourcesByTypeRessource(@PathVariable Long typeRessourceId){
        return ressourceService.getAllRessourcesByTypeRessource(typeRessourceId);
    }
    @GetMapping(path = "/ressources/ue/{ueId}")
    public List<RessourceResponseDTO> allRessourcesByUe(@PathVariable Long ueId){
        return ressourceService.getAllRessourcesByUe(ueId);
    }
    @PutMapping(path = "/ressources/{id}")
    public RessourceResponseDTO updateRessource(@PathVariable Long id, @RequestBody RessourceRequestDTO ressourceRequestDTO){
        return ressourceService.updateRessource(id, ressourceRequestDTO);
    }
    @PutMapping(path = "/ressources/{id}/file")
    public RessourceResponseDTO updateRessourceFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ressourceService.updateRessourceFile(id, file);
    }
    @DeleteMapping(path = "/ressources/{id}")
    public void delete(@PathVariable Long id){
        ressourceService.deleteRessource(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}