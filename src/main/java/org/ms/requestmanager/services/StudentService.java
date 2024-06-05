package org.ms.requestmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ms.requestmanager.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StudentService {
    StudentResponseDTO saveStudent(MultipartFile file, String studentIn) throws JsonProcessingException;
    StudentResponseDTO getStudent(Long studentId);
    ResponseEntity<byte[]> getStudentPicture(Long studentId);
    Path getStudentPathPicture(Long studentId);
    StudentResponseDTO getStudentByUser(String userId);
    List<StudentResponseDTO> getAllStudents();
    List<StudentResponseDTO> getAllStudentsByLevel(Long levelId);
    StudentResponseDTO updateStudent(Long studentId, StudentRequestDTO studentRequestDTO);
    StudentResponseDTO updateStudentPicture(Long studentId, MultipartFile file);
    void deleteStudent(Long studentId);
}
