package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.CommentRequest;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Request;
import org.ms.requestmanager.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRequestRepository extends JpaRepository<CommentRequest, Long> {
    List<CommentRequest> findByRequest(Request request);
    List<CommentRequest> findByPersonal(Personal personal);
    List<CommentRequest> findByStudent(Student student);
    List<CommentRequest> findByRequestAndPersonal(Request request, Personal personal);
    List<CommentRequest> findByRequestAndStudent(Request request, Student student);
}
