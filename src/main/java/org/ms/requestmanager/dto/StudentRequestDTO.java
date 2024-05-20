package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class StudentRequestDTO {
    private String matricule;
    private String firstname;
    private String lastname;
    private String email;
    private Long levelId;
    private String userId;
}
