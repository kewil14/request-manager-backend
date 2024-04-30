package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.Department;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.TypePersonal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
    List<Personal> findByTypePersonal(TypePersonal typePersonal);
    List<Personal> findByDepartment(Department department);
}
