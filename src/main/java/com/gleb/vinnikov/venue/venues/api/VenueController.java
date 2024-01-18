package com.gleb.vinnikov.venue.venues.api;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.venues.services.VenueService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Venues", description = "Методы для работы с заведениями.")
public class VenueController {

    private final VenueService venueService;

    @Operation(
            summary = "Получить данные о заведении по его id.",
            description = "Принимает id зведения.\nВ случае, если данный id соответствует зарегистрированному в " +
                    "системе заведению, то возвращает информацию о нем."
    )
    @GetMapping(value = "/venue-by-id")
    public ResponseEntity<VenueResponse> getById(
            @AuthenticationPrincipal User principal,
            @RequestParam UUID id) {
        return ResponseEntity.ok(venueService.getById(id));
    }

    @Operation(
            summary = "Получить данные о заведении по его idName.",
            description = "Принимает idName зведения.\nВ случае, если данный idName соответствует зарегистрированному в " +
                    "системе заведению, то возвращает информацию о нем."
    )
    @GetMapping(value = "/venue-by-id-name")
    public ResponseEntity<VenueResponse> getByIdName(
            @AuthenticationPrincipal User principal,
            @RequestParam String name) {
        return ResponseEntity.ok(venueService.getByIdName(name));
    }

    @Operation(
            summary = "Получить данные о заведениях по displayName.",
            description = "Принимает displayName зведения.\nВозвращает все заведения с переданным displayName."
    )
    @GetMapping(value = "/venue-by-display-name")
    public ResponseEntity<List<VenueResponse>> getByDisplayName(
            @AuthenticationPrincipal User principal,
            @RequestParam String name) {
        return ResponseEntity.ok(venueService.getByDisplayName(name));
    }

    @Operation(
            summary = "Получить данные о заведениях по префиксу названия",
            description = "Принимает префикс названия.\nВозвращает все заведения, у которых idName или displayName " +
                    "имеют переданный префикс."
    )
    @GetMapping(value = "/venue-by-display-name-prefix")
    public ResponseEntity<List<VenueResponse>> getByDisplayNamePrefix(
            @AuthenticationPrincipal User principal,
            @RequestParam(name = "name_prefix") String namePrefix) {
        return ResponseEntity.ok(venueService.getByNamePrefix(namePrefix));
    }

    @Operation(
            summary = "Добавить новое заведение.",
            description = "Принимает информацию о новом заведении и регистрирует его в системе.\nПереданный параметр " +
                    "idName должен быть уникальным среди уже зарегистрированных заведений.\nВозвращает информацию о " +
                    "зарегистрированном заведении, если переданная информация корректна."
    )
    @PostMapping(value = "/add-venue",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponse> addVenue(
            @AuthenticationPrincipal User principal,
            @Valid @RequestBody VenueCreationData venueCreationData) {
        return ResponseEntity.ok(venueService.addVenue(venueCreationData, principal));
    }

}
