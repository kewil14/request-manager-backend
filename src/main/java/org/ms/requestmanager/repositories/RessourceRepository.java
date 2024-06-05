package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.Ressource;
import org.ms.requestmanager.entities.TypeRessource;
import org.ms.requestmanager.entities.Ue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RessourceRepository extends JpaRepository<Ressource, Long> {
    List<Ressource> findByTypeRessource(TypeRessource typeRessource);
    List<Ressource> findByUe(Ue ue);
}
