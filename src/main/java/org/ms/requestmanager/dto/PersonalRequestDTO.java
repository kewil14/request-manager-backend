package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class PersonalRequestDTO {
    private String grade;
    private String firstname;
    private String lastname;
    private Long userId;
    private Long typePersonalId;
    private Long departmentId;
}
