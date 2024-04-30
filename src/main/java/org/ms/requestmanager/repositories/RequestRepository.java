package org.ms.requestmanager.repositories;

import org.ms.requestmanager.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByTypeRequest(TypeRequest typeRequest);
    List<Request> findByStudent(Student student);
    List<Request> findByPersonal(Personal personal);
    List<Request> findByUe(Ue ue);
    List<Request> findByTypeRequestAndState(TypeRequest typeRequest, Long stateId);
    List<Request> findByStudentAndState(Student student, Long stateId);
    List<Request> findByPersonalAndState(Personal personal, Long stateId);
    List<Request> findByUeAndState(Ue ue, Long stateId);
    List<Request> findByState(Long state);
}
