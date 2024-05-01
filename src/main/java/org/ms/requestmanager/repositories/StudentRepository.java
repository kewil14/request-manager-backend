package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.AppUser;
import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByMatricule(String matricule);
    List<Student> findByLevel(Level level);
    Student findByAppUser(AppUser appUser);
}
