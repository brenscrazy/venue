package com.gleb.vinnikov.social_network.db.repos;

import com.gleb.vinnikov.social_network.db.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VenueRepo extends JpaRepository<Venue, UUID> {

    Optional<Venue> findByIdName(String idName);
    List<Venue> findAllByDisplayName(String displayName);
    List<Venue> findAllByDisplayNameOrIdNameStartingWithOrderByDisplayName(String displayName, String idName);

}
