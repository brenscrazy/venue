package com.gleb.vinnikov.venue.events.api;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.events.services.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Events", description = "Методы для работы с событиями.")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping(
            value = "create-event"
    )
    public ResponseEntity<EventResponse> createEvent(
            @AuthenticationPrincipal User principal,
            @RequestBody EventRequest eventRequest
    ) {
        return ResponseEntity.ok(eventService.createEvent(principal, eventRequest));
    }

    @GetMapping(
            value = "get-event"
    )
    public ResponseEntity<EventResponse> getEvent(
            @AuthenticationPrincipal User principal,
            @RequestParam UUID eventId
    ) {
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @GetMapping(
            value = "get-events-by-venue-id-name"
    )
    public ResponseEntity<List<EventResponse>> getEventsByVenue(
            @AuthenticationPrincipal User principal,
            @RequestParam String venueIdName
    ) {
        return ResponseEntity.ok(eventService.getEventsByVenueIdName(venueIdName));
    }


}
