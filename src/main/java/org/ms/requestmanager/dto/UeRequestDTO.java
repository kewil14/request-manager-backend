package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class UeRequestDTO {
    private String code;
    private String title;
    private Long semester;
    private Long levelId;
    private Long personalId;
}
