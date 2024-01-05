package com.gleb.vinnikov.social_network.venues.api;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.venues.services.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class VenueController {

    private final VenueService venueService;

    @GetMapping(value = "/venue-by-id")
    public ResponseEntity<VenueResponse> getById(
            Principal principal,
            @RequestParam UUID id) {
        return ResponseEntity.ok(venueService.getById(id));
    }

    @GetMapping(value = "/venue-by-id-name")
    public ResponseEntity<VenueResponse> getByIdName(
            Principal principal,
            @RequestParam String name) {
        return ResponseEntity.ok(venueService.getByIdName(name));
    }

    @GetMapping(value = "/venue-by-display-name")
    public ResponseEntity<List<VenueResponse>> getByDisplayName(
            Principal principal,
            @RequestParam String displayName) {
        return ResponseEntity.ok(venueService.getByDisplayName(displayName));
    }

    @GetMapping(value = "/venue-by-display-name-prefix")
    public ResponseEntity<List<VenueResponse>> getByDisplayNamePrefix(
            Principal principal,
            @RequestParam String displayName) {
        return ResponseEntity.ok(venueService.getByNamePrefix(displayName));
    }

    @PostMapping(value = "/add-venue",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponse> addVenue(
            Authentication principal,
            @Valid @RequestBody VenueCreationData venueCreationData) {
        return ResponseEntity.ok(venueService.addVenue(venueCreationData, (User) principal.getPrincipal()));
    }

}
