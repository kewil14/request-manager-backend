package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Request;

import java.time.Instant;

@Data
public class TransfertRequestResponseDTO {
    private Long id;
    private String message;
    private Request request;
    private Personal personal;
    private Instant createdAt;
    private Instant updatedAt;
}
