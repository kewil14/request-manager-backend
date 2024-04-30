package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.TypeRessource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRessourceRepository extends JpaRepository<TypeRessource, Long> {
    TypeRessource findByName(String name);
}
