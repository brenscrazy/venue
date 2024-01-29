package com.gleb.vinnikov.venue.events.api;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.events.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Tag(name = "Events", description = "Методы для работы с событиями.")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(
            summary = "Создать событие",
            description = "Принимает заведение, дату и название события. В случае, если переданная информация " +
                    "корректна, создает новое событие и возвращает его."
    )
    @PostMapping(
            value = "create-event",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventResponse> createEvent(
            @AuthenticationPrincipal User principal,
            @RequestBody EventRequest eventRequest
    ) {
        return ResponseEntity.ok(eventService.createEvent(principal, eventRequest));
    }

    @Operation(
            summary = "Получить событие",
            description = "По id события возвращает информацию о нем."
    )
    @GetMapping(
            value = "get-event",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<EventResponse> getEvent(
            @AuthenticationPrincipal User principal,
            @RequestParam UUID id
    ) {
        return ResponseEntity.ok(eventService.getEvent(id));
    }

    @Operation(
            summary = "Получить список событий.",
            description = "Получить события в системе. Если указан параметр venueIdName, то возвращаются только события " +
                    "в указанном заведении. Если указан параметр before, то возвращаются только события до указанной " +
                    "даты. Если указан параметр after, то возвращаются только события после указанной даты."
    )
    @PostMapping(
            value = "get-events",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<EventResponse>> getEvents(
            @AuthenticationPrincipal User principal,
            @RequestBody GetEventRequest request
    ) {
        return ResponseEntity.ok(eventService.getEvents(request));
    }


}
