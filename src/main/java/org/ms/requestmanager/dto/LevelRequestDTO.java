package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class LevelRequestDTO {
    private String name;
    private Long requestId;
    private Long departmentId;
    private Long personalId;

}
