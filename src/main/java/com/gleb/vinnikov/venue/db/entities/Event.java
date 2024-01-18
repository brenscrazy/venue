package com.gleb.vinnikov.venue.db.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "takes_place_at_venue_id", nullable = false)
    private Venue takesPlaceAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Timestamp eventDate;

    @Column(nullable = false)
    private String displayName;

}
