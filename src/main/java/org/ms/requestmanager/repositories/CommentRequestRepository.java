package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.CommentRequest;
import org.ms.requestmanager.entities.Personal;
import org.ms.requestmanager.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRequestRepository extends JpaRepository<CommentRequest, Long> {
    List<CommentRequest> findByRequest(Request request);
    List<CommentRequest> findByPersonal(Personal personal);
}
