package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.AppUser;
import org.ms.requestmanager.entities.Level;

import java.time.Instant;

@Data
public class StudentResponseDTO {
    private Long id;
    private String matricule;
    private String firstname;
    private String lastname;
    private Level level;
    private AppUser appUser;
    private Instant createdAt;
    private Instant updatedAt;
}
