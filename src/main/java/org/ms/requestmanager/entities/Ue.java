package org.ms.requestmanager.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
public class Ue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String title;

    private Long semester; // 1, 2

    //One Ue to One level
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "levelId")
    private Level level;

    //One Ue is teaching by one personal
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personalId")
    private Personal personal;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //link to many ressource
    @OneToMany(mappedBy = "ue", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Ressource> ressources;

    //link to many requests
    @OneToMany(mappedBy = "ue", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Request> requests;
}
