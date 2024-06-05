package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String Username);
}
