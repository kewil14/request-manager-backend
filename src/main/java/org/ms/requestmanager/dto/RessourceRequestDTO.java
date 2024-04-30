package org.ms.requestmanager.dto;

import lombok.Data;

@Data
public class RessourceRequestDTO {
    private String name;
    private Long typeRessourceId;
    private Long ueId;
}
