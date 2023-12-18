package com.gleb.vinnikov.social_network.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.nio.file.Path;
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
    @JoinColumn(name = "userId")
    private User uploadedByUser;

}
