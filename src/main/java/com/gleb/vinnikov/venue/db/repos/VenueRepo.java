package com.gleb.vinnikov.venue.db.repos;

import com.gleb.vinnikov.venue.db.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VenueRepo extends JpaRepository<Venue, UUID> {

    Optional<Venue> findByIdName(String idName);

    List<Venue> findAllByDisplayNameOrderByIdName(String displayName);

    List<Venue> findAllByDisplayNameStartingWithOrIdNameStartingWithOrderByDisplayNameAscIdNameAsc(String displayName,
                                                                                                   String idName);

    @Query("SELECT id FROM Venue v WHERE v.idName = ?1")
    Optional<UUID> findIdByIdName(String idName);

    boolean existsByIdName(String venueIdName);

}
