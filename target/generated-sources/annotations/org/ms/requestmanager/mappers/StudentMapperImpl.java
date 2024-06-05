package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.StudentRequestDTO;
import org.ms.requestmanager.dto.StudentResponseDTO;
import org.ms.requestmanager.entities.Student;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T12:31:25+0100",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.3 (Amazon.com Inc.)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student studentRequestDTOToStudent(StudentRequestDTO studentRequestDTO) {
        if ( studentRequestDTO == null ) {
            return null;
        }

        Student student = new Student();

        student.setMatricule( studentRequestDTO.getMatricule() );
        student.setFirstname( studentRequestDTO.getFirstname() );
        student.setLastname( studentRequestDTO.getLastname() );
        student.setEmail( studentRequestDTO.getEmail() );

        return student;
    }

    @Override
    public StudentResponseDTO studentToStudentResponseDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();

        studentResponseDTO.setId( student.getId() );
        studentResponseDTO.setMatricule( student.getMatricule() );
        studentResponseDTO.setFirstname( student.getFirstname() );
        studentResponseDTO.setLastname( student.getLastname() );
        studentResponseDTO.setEmail( student.getEmail() );
        studentResponseDTO.setPicture( student.getPicture() );
        studentResponseDTO.setLevel( student.getLevel() );
        studentResponseDTO.setAppUser( student.getAppUser() );
        studentResponseDTO.setCreatedAt( student.getCreatedAt() );
        studentResponseDTO.setUpdatedAt( student.getUpdatedAt() );

        return studentResponseDTO;
    }
}
