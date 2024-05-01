package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.TypePersonalRequestDTO;
import org.ms.requestmanager.dto.TypePersonalResponseDTO;
import org.ms.requestmanager.services.TypePersonalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class TypePersonalRestAPI {
    private final TypePersonalService typePersonalService;

    public TypePersonalRestAPI(TypePersonalService typePersonalService) {
        this.typePersonalService = typePersonalService;
    }
    @PostMapping(path = "/typePersonals")
    public TypePersonalResponseDTO save(@RequestBody TypePersonalRequestDTO typePersonalRequestDTO){
        return typePersonalService.saveTypePersonal(typePersonalRequestDTO);
    }
    @GetMapping(path = "/typePersonals/{id}")
    public TypePersonalResponseDTO getTypePersonal(@PathVariable Long id){
        return typePersonalService.getTypePersonal(id);
    }
    @GetMapping(path = "/typePersonals")
    public List<TypePersonalResponseDTO> allTypePersonals(){
        return typePersonalService.getAllTypePersonals();
    }
    @PutMapping(path = "/typePersonals/{id}")
    public TypePersonalResponseDTO updateTypePersonal(@PathVariable Long id, @RequestBody TypePersonalRequestDTO typePersonalRequestDTO){
        return typePersonalService.updateTypePersonal(id, typePersonalRequestDTO);
    }
    @DeleteMapping(path = "/typePersonals/{id}")
    public void delete(@PathVariable Long id){
        typePersonalService.deleteTypePersonal(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}