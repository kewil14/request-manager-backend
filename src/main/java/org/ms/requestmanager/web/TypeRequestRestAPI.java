package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.TypeRequestRequestDTO;
import org.ms.requestmanager.dto.TypeRequestResponseDTO;
import org.ms.requestmanager.services.TypeRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class TypeRequestRestAPI {
    private final TypeRequestService typeRequestService;

    public TypeRequestRestAPI(TypeRequestService typeRequestService) {
        this.typeRequestService = typeRequestService;
    }
    @PostMapping(path = "/typeRequests")
    public TypeRequestResponseDTO save(@RequestBody TypeRequestRequestDTO typeRequestRequestDTO){
        return typeRequestService.saveTypeRequest(typeRequestRequestDTO);
    }
    @GetMapping(path = "/typeRequests/{id}")
    public TypeRequestResponseDTO getTypeRequest(@PathVariable Long id){
        return typeRequestService.getTypeRequest(id);
    }
    @GetMapping(path = "/typeRequests")
    public List<TypeRequestResponseDTO> allTypeRequests(){
        return typeRequestService.getAllTypeRequests();
    }
    @PutMapping(path = "/typeRequests/{id}")
    public TypeRequestResponseDTO updateTypeRequest(@PathVariable Long id, @RequestBody TypeRequestRequestDTO typeRequestRequestDTO){
        return typeRequestService.updateTypeRequest(id, typeRequestRequestDTO);
    }
    @DeleteMapping(path = "/typeRequests/{id}")
    public void delete(@PathVariable Long id){
        typeRequestService.deleteTypeRequest(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}