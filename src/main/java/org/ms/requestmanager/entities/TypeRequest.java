package org.ms.requestmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
public class TypeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; //IDENTIFICATION, NOTE_CC, NOTE_EE, AUTORISATION_ABSENCE, AUTORISATION_EVALUATION
    //REQUEST_ACADEMIC_TRANSCRIPT, REQUEST_DIPLOMA, OTHER

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //link to many requests
    @OneToMany(mappedBy = "typeRequest")
    @JsonIgnore
    private List<Request> requests;
}
