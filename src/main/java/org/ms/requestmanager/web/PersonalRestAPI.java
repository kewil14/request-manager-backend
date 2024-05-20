package org.ms.requestmanager.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.PersonalResponseDTO;
import org.ms.requestmanager.dto.PersonalRequestDTO;
import org.ms.requestmanager.services.PersonalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PersonalRestAPI {
    private final PersonalService personalService;

    public PersonalRestAPI(PersonalService personalService) {
        this.personalService = personalService;
    }
    @PostMapping(path = "/personals")
    public PersonalResponseDTO save(@RequestParam("file") MultipartFile file, String personalIn) throws JsonProcessingException {
        return personalService.savePersonal(file, personalIn);
    }
    @GetMapping(path = "/personals/{id}")
    public PersonalResponseDTO getPersonal(@PathVariable Long id){
        return personalService.getPersonal(id);
    }
    @GetMapping(path = "/personals/picture/{id}")
    public ResponseEntity<byte[]> getPersonalFile(@PathVariable Long id) {
        return personalService.getPersonalPicture(id);
    }
    @GetMapping(path = "/personals/pathpicture/{id}")
    public Path getPersonalPathFile(@PathVariable Long id) {
        return personalService.getPersonalPathPicture(id);
    }
    @GetMapping(path = "/personals/ByUser/{id}")
    public PersonalResponseDTO getPersonalByUserId(@PathVariable String id){
        return personalService.getPersonalByUser(id);
    }
    @GetMapping(path = "/personals")
    public List<PersonalResponseDTO> allPersonals(){
        return personalService.getAllPersonals();
    }
    @GetMapping(path = "/personals/typePersonal/{typePersonalId}")
    public List<PersonalResponseDTO> allPersonalsByTypePersonal(@PathVariable Long typePersonalId){
        return personalService.getAllPersonalsByTypePersonal(typePersonalId);
    }
    @GetMapping(path = "/personals/department/{departmentId}")
    public List<PersonalResponseDTO> allPersonalsByDepartment(@PathVariable Long departmentId){
        return personalService.getAllPersonalsByDepartment(departmentId);
    }
    @PutMapping(path = "/personals/{id}")
    public PersonalResponseDTO updatePersonal(@PathVariable Long id, @RequestBody PersonalRequestDTO personalRequestDTO){
        return personalService.updatePersonal(id, personalRequestDTO);
    }
    @PutMapping(path = "/personals/{id}/picture")
    public PersonalResponseDTO updatePersonalPicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return personalService.updatePersonalPicture(id, file);
    }
    @DeleteMapping(path = "/personals/{id}")
    public void delete(@PathVariable Long id){
        personalService.deletePersonal(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}