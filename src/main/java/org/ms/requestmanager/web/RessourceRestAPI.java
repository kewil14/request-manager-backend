package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.RessourceRequestDTO;
import org.ms.requestmanager.dto.RessourceResponseDTO;
import org.ms.requestmanager.services.RessourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class RessourceRestAPI {
    private final RessourceService ressourceService;

    public RessourceRestAPI(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }
    @PostMapping(path = "/ressources")
    public RessourceResponseDTO save(@RequestBody RessourceRequestDTO ressourceRequestDTO){
        return ressourceService.saveRessource(ressourceRequestDTO);
    }
    @GetMapping(path = "/ressources/{id}")
    public RessourceResponseDTO getRessource(@PathVariable Long id){
        return ressourceService.getRessource(id);
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
    @DeleteMapping(path = "/ressources/{id}")
    public void delete(@PathVariable Long id){
        ressourceService.deleteRessource(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}