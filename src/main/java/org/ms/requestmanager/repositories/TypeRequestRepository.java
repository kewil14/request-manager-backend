package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.TypeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRequestRepository extends JpaRepository<TypeRequest, Long> {
    TypeRequest findByName(String name);
}
