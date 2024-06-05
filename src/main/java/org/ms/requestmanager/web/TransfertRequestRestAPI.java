package org.ms.requestmanager.web;

import org.ms.requestmanager.dto.TransfertRequestRequestDTO;
import org.ms.requestmanager.dto.TransfertRequestResponseDTO;
import org.ms.requestmanager.services.TransfertRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class TransfertRequestRestAPI {
    private final TransfertRequestService transfertRequestService;

    public TransfertRequestRestAPI(TransfertRequestService transfertRequestService) {
        this.transfertRequestService = transfertRequestService;
    }
    @PostMapping(path = "/transfertRequests")
    public TransfertRequestResponseDTO save(@RequestBody TransfertRequestRequestDTO transfertRequestRequestDTO){
        return transfertRequestService.saveTransfertRequest(transfertRequestRequestDTO);
    }
    @GetMapping(path = "/transfertRequests/{id}")
    public TransfertRequestResponseDTO getTransfertRequest(@PathVariable Long id){
        return transfertRequestService.getTransfertRequest(id);
    }
    @GetMapping(path = "/transfertRequests")
    public List<TransfertRequestResponseDTO> allTransfertRequests(){
        return transfertRequestService.getAllTransfertRequests();
    }
    @GetMapping(path = "/transfertRequests/request/{requestId}")
    public List<TransfertRequestResponseDTO> allTransfertRequestsByRequest(@PathVariable Long requestId){
        return transfertRequestService.getAllTransfertRequestsByRequest(requestId);
    }
    @GetMapping(path = "/transfertRequests/request/{requestId}/personal/{personalId}")
    public List<TransfertRequestResponseDTO> allTransfertRequestsByRequestAndPersonal(@PathVariable Long requestId, @PathVariable Long personalId){
        return transfertRequestService.getAllTransfertRequestsByRequestAndPersonal(requestId, personalId);
    }
    @GetMapping(path = "/transfertRequests/personal/{personalId}")
    public List<TransfertRequestResponseDTO> allTransfertRequestsByPersonal(@PathVariable Long personalId){
        return transfertRequestService.getAllTransfertRequestsByPersonal(personalId);
    }
    @PutMapping(path = "/transfertRequests/{id}")
    public TransfertRequestResponseDTO updateTransfertRequest(@PathVariable Long id, @RequestBody TransfertRequestRequestDTO transfertRequestRequestDTO){
        return transfertRequestService.updateTransfertRequest(id, transfertRequestRequestDTO);
    }
    @DeleteMapping(path = "/transfertRequests/{id}")
    public void delete(@PathVariable Long id){
        transfertRequestService.deleteTransfertRequest(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}