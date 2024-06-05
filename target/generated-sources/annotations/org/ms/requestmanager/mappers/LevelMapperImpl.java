package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.LevelRequestDTO;
import org.ms.requestmanager.dto.LevelResponseDTO;
import org.ms.requestmanager.entities.Level;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class LevelMapperImpl implements LevelMapper {

    @Override
    public Level levelRequestDTOToLevel(LevelRequestDTO levelRequestDTO) {
        if ( levelRequestDTO == null ) {
            return null;
        }

        Level level = new Level();

        level.setName( levelRequestDTO.getName() );

        return level;
    }

    @Override
    public LevelResponseDTO levelToLevelResponseDTO(Level level) {
        if ( level == null ) {
            return null;
        }

        LevelResponseDTO levelResponseDTO = new LevelResponseDTO();

        levelResponseDTO.setId( level.getId() );
        levelResponseDTO.setName( level.getName() );
        levelResponseDTO.setDepartment( level.getDepartment() );
        levelResponseDTO.setPersonal( level.getPersonal() );
        levelResponseDTO.setCreatedAt( level.getCreatedAt() );
        levelResponseDTO.setUpdatedAt( level.getUpdatedAt() );

        return levelResponseDTO;
    }
}
