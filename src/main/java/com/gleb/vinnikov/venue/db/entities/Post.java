package com.gleb.vinnikov.venue.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "authorId")
//    @Column(nullable = false)
//    private User author;

    private String header;

    private String postText;
    
    @OneToMany
    @JoinColumn(name = "fileId")
    private List<File> image;

}
