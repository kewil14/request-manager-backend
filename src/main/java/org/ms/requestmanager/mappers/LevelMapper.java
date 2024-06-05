package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.LevelRequestDTO;
import org.ms.requestmanager.dto.LevelResponseDTO;
import org.ms.requestmanager.entities.Level;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    Level levelRequestDTOToLevel(LevelRequestDTO levelRequestDTO);
    LevelResponseDTO levelToLevelResponseDTO(Level level);
}
