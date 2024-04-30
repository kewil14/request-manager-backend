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
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    //link to one personal has chief
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personalId")
    private Personal personal;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //link to many levels
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Level> levels;

    //link to many personals
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Personal> personals;
}
