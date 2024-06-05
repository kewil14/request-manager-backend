package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.LevelRequestDTO;
import org.ms.requestmanager.dto.LevelResponseDTO;
import org.ms.requestmanager.services.LevelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class LevelRestAPI {
    private final LevelService levelService;

    public LevelRestAPI(LevelService levelService) {
        this.levelService = levelService;
    }
    @PostMapping(path = "/levels")
    public LevelResponseDTO save(@RequestBody LevelRequestDTO levelRequestDTO){
        return levelService.saveLevel(levelRequestDTO);
    }
    @GetMapping(path = "/levels/{id}")
    public LevelResponseDTO getLevel(@PathVariable Long id){
        return levelService.getLevel(id);
    }
    @GetMapping(path = "/levels")
    public List<LevelResponseDTO> allLevels(){
        return levelService.getAllLevels();
    }
    @GetMapping(path = "/levels/department/{departmentId}")
    public List<LevelResponseDTO> allLevelsByDepartment(@PathVariable Long departmentId){
        return levelService.getAllLevelsByDepartment(departmentId);
    }
    @GetMapping(path = "/levels/personal/{personalId}")
    public List<LevelResponseDTO> allLevelsByPersonal(@PathVariable Long personalId){
        return levelService.getAllLevelsByPersonal(personalId);
    }
    @PutMapping(path = "/levels/{id}")
    public LevelResponseDTO updateLevel(@PathVariable Long id, @RequestBody LevelRequestDTO levelRequestDTO){
        return levelService.updateLevel(id, levelRequestDTO);
    }
    @DeleteMapping(path = "/levels/{id}")
    public void delete(@PathVariable Long id){
        levelService.deleteLevel(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}