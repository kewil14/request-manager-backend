package org.ms.requestmanager.entities;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
public class Ressource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String link;

    //link to one type of ressource
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeRessourceId")
    private TypeRessource typeRessource;

    //link to one ue
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ueId")
    private Ue ue;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
