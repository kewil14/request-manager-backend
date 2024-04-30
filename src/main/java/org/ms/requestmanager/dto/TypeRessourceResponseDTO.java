package org.ms.requestmanager.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class TypeRessourceResponseDTO {
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
}
