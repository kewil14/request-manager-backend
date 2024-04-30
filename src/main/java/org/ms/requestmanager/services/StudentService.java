package org.ms.requestmanager.services;

import org.ms.requestmanager.dto.*;

import java.util.List;

public interface StudentService {
    StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO);
    StudentResponseDTO getStudent(Long studentId);
    List<StudentResponseDTO> getAllStudents();
    List<StudentResponseDTO> getAllStudentsByLevel(Long levelId);
    StudentResponseDTO updateStudent(Long studentId, StudentRequestDTO studentRequestDTO);
    void deleteStudent(Long studentId);
}
