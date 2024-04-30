package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.Department;
import org.ms.requestmanager.entities.Personal;

import java.time.Instant;

@Data
public class LevelResponseDTO {
    private Long id;
    private String name;
    private Department department;
    private Personal personal;
    private Instant createdAt;
    private Instant updatedAt;
}
