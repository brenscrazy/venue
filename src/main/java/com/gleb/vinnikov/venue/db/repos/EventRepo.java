package com.gleb.vinnikov.venue.db.repos;

import com.gleb.vinnikov.venue.db.entities.Event;
import com.gleb.vinnikov.venue.db.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface EventRepo extends JpaRepository<Event, UUID> {

    List<Event> findByTakesPlaceAt(Venue takesPlaceAt);

    @Query("select e from Event e where e.takesPlaceAt = ?1 and ?2 < e.eventDate and e.eventDate < ?3")
    List<Event> findByTakesPlaceAtAndBeforeAndAfter(Venue takesPlaceAt, Timestamp after, Timestamp before);

    @Query("select e from Event e where ?1 < e.eventDate and e.eventDate < ?2")
    List<Event> findByBeforeAndAfter(Timestamp after, Timestamp before);

}
