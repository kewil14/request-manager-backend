package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Request;
import org.ms.requestmanager.entities.TransfertRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransfertRequestRepository extends JpaRepository<TransfertRequest, Long> {
    List<TransfertRequest> findByRequest(Request request);
    List<TransfertRequest> findByPersonal(Personal personal);
    List<TransfertRequest> findByRequestAndPersonal(Request request, Personal personal);
}
