package com.gleb.vinnikov.venue.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class File {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String path;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploadedBy;

}
