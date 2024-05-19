package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.Request;

import java.time.Instant;

@Data
public class AttachmentResponseDTO {
    private Long id;
    private String name;
    private String link;
    private Request request;
    private Instant createdAt;
    private Instant updatedAt;
}
