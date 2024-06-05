package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.TypeRessourceRequestDTO;
import org.ms.requestmanager.dto.TypeRessourceResponseDTO;
import org.ms.requestmanager.services.TypeRessourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class TypeRessourceRestAPI {
    private final TypeRessourceService typeRessourceService;

    public TypeRessourceRestAPI(TypeRessourceService typeRessourceService) {
        this.typeRessourceService = typeRessourceService;
    }
    @PostMapping(path = "/typeRessources")
    public TypeRessourceResponseDTO save(@RequestBody TypeRessourceRequestDTO typeRessourceRequestDTO){
        return typeRessourceService.saveTypeRessource(typeRessourceRequestDTO);
    }
    @GetMapping(path = "/typeRessources/{id}")
    public TypeRessourceResponseDTO getTypeRessource(@PathVariable Long id){
        return typeRessourceService.getTypeRessource(id);
    }
    @GetMapping(path = "/typeRessources")
    public List<TypeRessourceResponseDTO> allTypeRessources(){
        return typeRessourceService.getAllTypeRessources();
    }
    @PutMapping(path = "/typeRessources/{id}")
    public TypeRessourceResponseDTO updateTypeRessource(@PathVariable Long id, @RequestBody TypeRessourceRequestDTO typeRessourceRequestDTO){
        return typeRessourceService.updateTypeRessource(id, typeRessourceRequestDTO);
    }
    @DeleteMapping(path = "/typeRessources/{id}")
    public void delete(@PathVariable Long id){
        typeRessourceService.deleteTypeRessource(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}