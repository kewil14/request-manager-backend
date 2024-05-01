package org.ms.requestmanager.mappers;

import javax.annotation.Generated;
import org.ms.requestmanager.dto.StudentRequestDTO;
import org.ms.requestmanager.dto.StudentResponseDTO;
import org.ms.requestmanager.entities.Student;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-01T08:50:54+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3.1 (Oracle Corporation)"
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
        studentResponseDTO.setLevel( student.getLevel() );
        studentResponseDTO.setAppUser( student.getAppUser() );
        studentResponseDTO.setCreatedAt( student.getCreatedAt() );
        studentResponseDTO.setUpdatedAt( student.getUpdatedAt() );

        return studentResponseDTO;
    }
}
