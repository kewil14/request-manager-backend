package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class CommentRequestRequestDTO {
    private String message;
    private Long requestId;
    private Long personalId;
    private Long studentId;
}
