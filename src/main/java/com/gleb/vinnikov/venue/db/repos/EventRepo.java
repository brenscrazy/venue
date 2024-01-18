package com.gleb.vinnikov.venue.db.repos;

import com.gleb.vinnikov.venue.db.entities.Event;
import com.gleb.vinnikov.venue.db.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepo extends JpaRepository<Event, UUID> {

    List<Event> findByTakesPlaceAt(Venue takesPlaceAt);

}
