package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Ue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UeRepository extends JpaRepository<Ue, Long> {
    Ue findByCode(String code);
    List<Ue> findByLevel(Level level);
    List<Ue> findByPersonal(Personal personal);
    List<Ue> findByLevelAndSemester(Level level, Long semester);
}
