package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class RequestRequestDTO {
    private String objet;
    private String message;
    private Long typeRequestId;
    private Long studentId;
    private Long ueId;
}
