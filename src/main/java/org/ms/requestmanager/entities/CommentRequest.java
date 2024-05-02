package org.ms.requestmanager.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@JsonIgnoreProperties({"request","personal"})
public class CommentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    //link to one request
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requestId")
    private Request request;

    //link to one personal
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personalId")
    private Personal personal;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
