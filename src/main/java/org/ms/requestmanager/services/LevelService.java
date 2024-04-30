package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface LevelService {
    LevelResponseDTO saveLevel(LevelRequestDTO levelRequestDTO);
    LevelResponseDTO getLevel(Long levelId);
    List<LevelResponseDTO> getAllLevels();
    List<LevelResponseDTO> getAllLevelsByDepartment(Long departmentId);
    List<LevelResponseDTO> getAllLevelsByPersonal(Long personalId);
    LevelResponseDTO updateLevel(Long levelId, LevelRequestDTO levelRequestDTO);
    void deleteLevel(Long levelId);
}
