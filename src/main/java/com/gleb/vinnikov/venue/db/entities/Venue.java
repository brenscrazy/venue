package com.gleb.vinnikov.venue.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String creatorUsername;

    @Column(nullable = false, unique = true)
    private String idName;

    @Column(nullable = false)
    private String displayName;

    public interface VenueIdOnly {

        public UUID getId();

    }

}
