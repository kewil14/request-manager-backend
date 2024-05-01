package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.RequestRequestDTO;
import org.ms.requestmanager.dto.RequestResponseDTO;
import org.ms.requestmanager.services.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class RequestRestAPI {
    private final RequestService requestService;

    public RequestRestAPI(RequestService requestService) {
        this.requestService = requestService;
    }
    @PostMapping(path = "/requests")
    public RequestResponseDTO save(@RequestBody RequestRequestDTO requestRequestDTO){
        return requestService.saveRequest(requestRequestDTO);
    }
    @GetMapping(path = "/requests/{id}")
    public RequestResponseDTO getRequest(@PathVariable Long id){
        return requestService.getRequest(id);
    }
    @GetMapping(path = "/requests")
    public List<RequestResponseDTO> allRequests(){
        return requestService.getAllRequests();
    }
    @GetMapping(path = "/requests/typeRequest/{typeRequestId}")
    public List<RequestResponseDTO> allRequestsByTypeRequest(@PathVariable Long typeRequestId){
        return requestService.getAllRequestsByTypeRequest(typeRequestId);
    }
    @GetMapping(path = "/requests/student/{studentId}")
    public List<RequestResponseDTO> allRequestsByStudent(@PathVariable Long studentId){
        return requestService.getAllRequestsByStudent(studentId);
    }
    @GetMapping(path = "/requests/personal/{personalId}")
    public List<RequestResponseDTO> allRequestsByPersonal(@PathVariable Long personalId){
        return requestService.getAllRequestsByPersonal(personalId);
    }
    @GetMapping(path = "/requests/ue/{ueId}")
    public List<RequestResponseDTO> allRequestsByUe(@PathVariable Long ueId){
        return requestService.getAllRequestsByUe(ueId);
    }
    @PutMapping(path = "/requests/{id}")
    public RequestResponseDTO updateRequest(@PathVariable Long id, @RequestBody RequestRequestDTO requestRequestDTO){
        return requestService.updateRequest(id, requestRequestDTO);
    }
    @PutMapping(path = "/{id}/archive")
    public void archive(@PathVariable Long id){
        requestService.archiveRequest(id);
    }
    @PutMapping(path = "/{id}/updateStatus/{status}")
    public void updateStatus(@PathVariable Long id, @PathVariable String status){
        requestService.updateStatusRequest(id, status);
    }
    @DeleteMapping(path = "/requests/{id}")
    public void delete(@PathVariable Long id){
        requestService.deleteRequest(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}