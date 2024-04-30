package org.ms.requestmanager.dto;

import lombok.Data;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Student;
import org.ms.requestmanager.entities.TypeRequest;
import org.ms.requestmanager.entities.Ue;

import java.time.Instant;

@Data
public class RequestResponseDTO {
    private Long id;
    private String objet;
    private String message;
    private String status;
    private Long state;
    private TypeRequest typeRequest;
    private Student student;
    private Personal personal;
    private Ue ue;
    private Instant createdAt;
    private Instant updatedAt;
}
