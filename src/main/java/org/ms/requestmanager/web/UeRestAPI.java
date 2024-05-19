package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.UeRequestDTO;
import org.ms.requestmanager.dto.UeResponseDTO;
import org.ms.requestmanager.services.UeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UeRestAPI {
    private final UeService ueService;

    public UeRestAPI(UeService ueService) {
        this.ueService = ueService;
    }
    @PostMapping(path = "/ues")
    public UeResponseDTO save(@RequestBody UeRequestDTO ueRequestDTO){
        return ueService.saveUe(ueRequestDTO);
    }
    @GetMapping(path = "/ues/{id}")
    public UeResponseDTO getUe(@PathVariable Long id){
        return ueService.getUe(id);
    }
    @GetMapping(path = "/ues")
    public List<UeResponseDTO> allUes(){
        return ueService.getAllUes();
    }
    @GetMapping(path = "/ues/level/{levelId}")
    public List<UeResponseDTO> allUesByLevel(@PathVariable Long levelId){
        return ueService.getAllUesByLevel(levelId);
    }
    @GetMapping(path = "/ues/level/{levelId}/semester/{semester}")
    public List<UeResponseDTO> allUesByLevelAndSemester(@PathVariable Long levelId, @PathVariable Long semester){
        return ueService.getAllUesByLevelAndSemester(levelId, semester);
    }
    @GetMapping(path = "/ues/personal/{personalId}")
    public List<UeResponseDTO> allUesByPersonal(@PathVariable Long personalId){
        return ueService.getAllUesByPersonal(personalId);
    }
    @PutMapping(path = "/ues/{id}")
    public UeResponseDTO updateUe(@PathVariable Long id, @RequestBody UeRequestDTO ueRequestDTO){
        return ueService.updateUe(id, ueRequestDTO);
    }
    @DeleteMapping(path = "/ues/{id}")
    public void delete(@PathVariable Long id){
        ueService.deleteUe(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}