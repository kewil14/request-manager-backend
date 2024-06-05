package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.Attachment;
import org.ms.requestmanager.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByRequest(Request request);
}
