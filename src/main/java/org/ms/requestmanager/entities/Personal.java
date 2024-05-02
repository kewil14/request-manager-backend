package org.ms.requestmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"department", "ues", "requests", "commentRequests", "transfertRequests"})
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grade; // ASSISTANT, CC, MC, PR

    private String firstname;

    private String lastname;

    //link to one personal to one User
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private AppUser appUser;

    //One personal -- One type of personal
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typePersonalId")
    private TypePersonal typePersonal;

    //One personal -- One department
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentId")
    private Department department;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //Teaching Many Ues
    @OneToMany(mappedBy = "personal", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Ue> ues;

    //link to many requests
    @OneToMany(mappedBy = "personal", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Request> requests;

    //link to many commentRequest
    @OneToMany(mappedBy = "personal", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CommentRequest> commentRequests;

    //link to many transfertRequest
    @OneToMany(mappedBy = "personal", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TransfertRequest> transfertRequests;
}
