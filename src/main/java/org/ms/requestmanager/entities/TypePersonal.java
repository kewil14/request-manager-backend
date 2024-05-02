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
public class TypePersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; //TEACHER, DIRECTOR, SECRETARY, CHIEF_SCHOOLING, CHIEF_STAGE

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //link to many levels
    @OneToMany(mappedBy = "typePersonal", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Personal> personals;
}
