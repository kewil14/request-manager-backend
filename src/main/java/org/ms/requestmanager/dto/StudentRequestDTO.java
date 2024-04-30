package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class StudentRequestDTO {
    private String name;
    private String matricule;
    private String firstname;
    private String lastname;
    private Long levelId;
    private Long userId;
}
