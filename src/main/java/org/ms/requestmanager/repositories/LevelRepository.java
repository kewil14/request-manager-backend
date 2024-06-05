package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.Department;
import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findByDepartment(Department department);
    List<Level> findByPersonal(Personal personal);
}
