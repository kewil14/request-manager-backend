package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.AppUser;
import org.ms.requestmanager.entities.Department;
import org.ms.requestmanager.entities.TypePersonal;

import java.time.Instant;

@Data
public class PersonalResponseDTO {
    private Long id;
    private String grade;
    private String firstname;
    private String lastname;
    private AppUser appUser;
    private TypePersonal typePersonal;
    private Department department;
    private Instant createdAt;
    private Instant updatedAt;
}
