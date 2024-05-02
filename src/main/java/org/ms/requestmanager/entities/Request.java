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
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objet;

    private String message;

    private String status; // SENDING, PENDING, TREATED, REJECTED

    private Long state; // 0 = ARCHIVE, 1 = ACTIVE

    //link to one typeRequest
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeRequestId")
    private TypeRequest typeRequest;

    //link to one student
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId")
    private Student student;

    //link to one personal
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personalId")
    private Personal personal;

    //link to one ue
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ueId")
    private Ue ue;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //link to many attachments
    @OneToMany(mappedBy = "request")
    @JsonIgnore
    private List<Attachment> attachments;

    //link to many commentRequest
    @OneToMany(mappedBy = "request")
    @JsonIgnore
    private List<CommentRequest> commentRequests;

    //link to many transfertRequest
    @OneToMany(mappedBy = "request")
    @JsonIgnore
    private List<TransfertRequest> transfertRequests;
}
