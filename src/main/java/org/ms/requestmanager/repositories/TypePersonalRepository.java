package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.TypePersonal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypePersonalRepository extends JpaRepository<TypePersonal, Long> {
    TypePersonal findByName(String name);
}
