package com.gleb.vinnikov.venue.events.services;

import com.gleb.vinnikov.venue.db.entities.Event;
import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.entities.Venue;
import com.gleb.vinnikov.venue.db.repos.EventRepo;
import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import com.gleb.vinnikov.venue.events.api.DateFields;
import com.gleb.vinnikov.venue.events.api.EventRequest;
import com.gleb.vinnikov.venue.events.api.EventResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final VenueRepo venueRepo;

    public EventResponse createEvent(@NonNull User creator, @NonNull EventRequest eventRequest) {
        Event event = eventRepo.save(Event.builder()
                .displayName(eventRequest.getDisplayName())
                .takesPlaceAt(venueRepo.findByIdName(eventRequest.getVenueIdName())
                        .orElseThrow(() -> new IllegalArgumentException("No venue with given name")))
                .eventDate(eventRequest.getDate().toTimestamp())
                .build());
        return eventToEventResponse(event);
    }

    public EventResponse getEvent(UUID eventId) {
        return eventToEventResponse(eventRepo.findById(eventId).orElseThrow(() ->
                new IllegalArgumentException("No event with given id")));
    }

    public List<EventResponse> getEventsByVenueIdName(String venueIdName) {
        Venue venue = venueRepo.findByIdName(venueIdName).orElseThrow(() -> new IllegalArgumentException(
                "No venue with given idName found"));
        return eventRepo.findByTakesPlaceAt(venue).stream().map(this::eventToEventResponse)
                .collect(Collectors.toList());
    }

    private EventResponse eventToEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .date(new DateFields(event.getEventDate()))
                .displayName(event.getDisplayName())
                .takesPlaceAt(event.getTakesPlaceAt().getIdName()).build();
    }

}
