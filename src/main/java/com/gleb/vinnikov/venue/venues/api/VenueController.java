package com.gleb.vinnikov.venue.venues.api;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.venues.services.VenueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Venues", description = "Methods to add/get/update/delete venues")
public class VenueController {

    private final VenueService venueService;

    @GetMapping(value = "/venue-by-id")
    public ResponseEntity<VenueResponse> getById(
            @AuthenticationPrincipal User principal,
            @RequestParam UUID id) {
        return ResponseEntity.ok(venueService.getById(id));
    }

    @GetMapping(value = "/venue-by-id-name")
    public ResponseEntity<VenueResponse> getByIdName(
            @AuthenticationPrincipal User principal,
            @RequestParam String name) {
        return ResponseEntity.ok(venueService.getByIdName(name));
    }

    @GetMapping(value = "/venue-by-display-name")
    public ResponseEntity<List<VenueResponse>> getByDisplayName(
            @AuthenticationPrincipal User principal,
            @RequestParam String name) {
        return ResponseEntity.ok(venueService.getByDisplayName(name));
    }

    @GetMapping(value = "/venue-by-display-name-prefix")
    public ResponseEntity<List<VenueResponse>> getByDisplayNamePrefix(
            @AuthenticationPrincipal User principal,
            @RequestParam(name = "name_prefix") String namePrefix) {
        return ResponseEntity.ok(venueService.getByNamePrefix(namePrefix));
    }

    @PostMapping(value = "/add-venue",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponse> addVenue(
            @AuthenticationPrincipal User principal,
            @Valid @RequestBody VenueCreationData venueCreationData) {
        return ResponseEntity.ok(venueService.addVenue(venueCreationData, principal));
    }

}
