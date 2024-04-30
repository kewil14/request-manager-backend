package org.ms.requestmanager.mappers;

import org.mapstruct.Mapper;
import org.ms.requestmanager.dto.StudentRequestDTO;
import org.ms.requestmanager.dto.StudentResponseDTO;
import org.ms.requestmanager.entities.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student studentRequestDTOToStudent(StudentRequestDTO studentRequestDTO);
    StudentResponseDTO studentToStudentResponseDTO(Student student);
}
