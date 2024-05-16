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
@JsonIgnoreProperties({"ues", "students"})
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //link to one department
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departmentId")
    private Department department;

    //link to one personal like President of jury
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personalId")
    private Personal personal;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    //link to many ues -- One level has many Ues
    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Ue> ues;

    //link to many students -- One level has many students
    @OneToMany(mappedBy = "level", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Student> students;
}
