package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.Level;
import org.ms.requestmanager.entities.Personal;

import java.time.Instant;

@Data
public class UeResponseDTO {
    private Long id;
    private String code;
    private String title;
    private Long semester;
    private Level level;
    private Personal personal;
    private Instant createdAt;
    private Instant updatedAt;
}
