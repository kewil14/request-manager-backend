package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.Personal;

import java.time.Instant;

@Data
public class DepartmentResponseDTO {
    private Long id;
    private String name;
    private String linkTimetableS1;
    private String linkTimetableS2;
    private Personal personal;
    private Instant createdAt;
    private Instant updatedAt;
}
