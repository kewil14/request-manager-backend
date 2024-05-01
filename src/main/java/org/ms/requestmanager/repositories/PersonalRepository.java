package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalRepository extends JpaRepository<Personal, Long> {
    List<Personal> findByTypePersonal(TypePersonal typePersonal);
    List<Personal> findByDepartment(Department department);
    Personal findByAppUser(AppUser appUser);
}
