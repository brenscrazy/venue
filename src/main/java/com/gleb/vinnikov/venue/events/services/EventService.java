package com.gleb.vinnikov.venue.events.services;

import com.gleb.vinnikov.venue.db.entities.Event;
import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.entities.Venue;
import com.gleb.vinnikov.venue.db.repos.EventRepo;
import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import com.gleb.vinnikov.venue.events.api.DateFields;
import com.gleb.vinnikov.venue.events.api.EventRequest;
import com.gleb.vinnikov.venue.events.api.EventResponse;
import com.gleb.vinnikov.venue.events.api.GetEventRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
                new NoSuchElementException("No event with given id")));
    }

    public List<EventResponse> getEvents(GetEventRequest request) {
        Optional<Venue> venueOptional = Optional.ofNullable(request.getVenueIdName())
                .map(venueIdName -> venueRepo.findByIdName(venueIdName)
                        .orElseThrow(() -> new IllegalArgumentException("No venue with given idName found")));
        Timestamp after = request.getAfter().toTimestamp();
        Timestamp before = request.getBefore().toTimestamp();
        return venueOptional.map(venue -> eventRepo.findByTakesPlaceAtAndBeforeAndAfter(venue, after, before))
                .orElse(eventRepo.findByBeforeAndAfter(after, before))
                .stream().map(this::eventToEventResponse).collect(Collectors.toList());
    }

    private EventResponse eventToEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .date(new DateFields(event.getEventDate()))
                .displayName(event.getDisplayName())
                .takesPlaceAt(event.getTakesPlaceAt().getIdName()).build();
    }

}
