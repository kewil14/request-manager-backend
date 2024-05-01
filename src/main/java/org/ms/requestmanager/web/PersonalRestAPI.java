package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.PersonalRequestDTO;
import org.ms.requestmanager.dto.PersonalResponseDTO;
import org.ms.requestmanager.services.PersonalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PersonalRestAPI {
    private final PersonalService personalService;

    public PersonalRestAPI(PersonalService personalService) {
        this.personalService = personalService;
    }
    @PostMapping(path = "/personals")
    public PersonalResponseDTO save(@RequestBody PersonalRequestDTO personalRequestDTO){
        return personalService.savePersonal(personalRequestDTO);
    }
    @GetMapping(path = "/personals/{id}")
    public PersonalResponseDTO getPersonal(@PathVariable Long id){
        return personalService.getPersonal(id);
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
    @DeleteMapping(path = "/personals/{id}")
    public void delete(@PathVariable Long id){
        personalService.deletePersonal(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}