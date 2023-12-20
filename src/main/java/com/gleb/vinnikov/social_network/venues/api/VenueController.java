package com.gleb.vinnikov.social_network.venues.api;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.entities.Venue;
import com.gleb.vinnikov.social_network.db.repos.VenueRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class VenueController {

    private final VenueRepo venueRepo;

    @GetMapping(value = "/venue")
    public ResponseEntity<Venue> getVenue(
            Principal principal,
            @RequestParam UUID uuid) {
        return ResponseEntity.ok(venueRepo.getReferenceById(uuid));
    }

    @PostMapping(value = "/venue",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Venue> addVenue(
            Authentication principal,
            @Valid @RequestBody VenueCreationData venueCreationData) {
        User user = (User) principal.getPrincipal();
        Venue venue = Venue.builder().name(venueCreationData.getName()).createdBy(user).build();
        return ResponseEntity.ok(venueRepo.save(venue));
    }

}
