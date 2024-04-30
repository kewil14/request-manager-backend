package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
    AppRole findByRolename(String rolename);
}
