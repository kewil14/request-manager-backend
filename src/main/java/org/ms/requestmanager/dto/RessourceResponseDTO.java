package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.TypeRessource;
import org.ms.requestmanager.entities.Ue;

import java.time.Instant;

@Data
public class RessourceResponseDTO {
    private Long id;
    private String name;
    private TypeRessource typeRessource;
    private Ue ue;
    private Instant createdAt;
    private Instant updatedAt;
}
