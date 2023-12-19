package com.gleb.vinnikov.social_network.venues.api;

import com.gleb.vinnikov.social_network.db.entities.Venue;
import com.gleb.vinnikov.social_network.db.repos.VenueRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
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
            Principal principal,
            @RequestParam UUID uuid) {
        return ResponseEntity.ok(venueRepo.getReferenceById(uuid));
    }

}
