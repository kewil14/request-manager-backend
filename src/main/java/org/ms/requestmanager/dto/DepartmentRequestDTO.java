package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class DepartmentRequestDTO {
    private String name;
    private Long requestId;
    private Long personalId;
}
