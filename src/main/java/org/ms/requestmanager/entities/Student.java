package org.ms.requestmanager.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"requests"})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String matricule;

    private String firstname;

    private String lastname;

    private String email;

    private String picture;

    //One student -- One level
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "levelId")
    private Level level;

    //link to one student to one User
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private AppUser appUser;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //link to many requests
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Request> requests;
}
