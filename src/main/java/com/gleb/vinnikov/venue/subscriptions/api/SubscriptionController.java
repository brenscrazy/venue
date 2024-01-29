package com.gleb.vinnikov.venue.subscriptions.api;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.subscriptions.services.SubscriptionService;
import com.gleb.vinnikov.venue.users.api.UserResponsePublic;
import com.gleb.vinnikov.venue.utils.Message;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Методы для работы с подписками пользователей на заведения")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(
            summary = "Подписаться на заведение по его id.",
            description = "Принимает id заведения.\nВ случае, если данный id соответствует зарегистрированному в " +
                    "системе заведению, то оформляется подписка пользователя, которому принадлежит нынешний JWT " +
                    "токен на данное заведение или возвращается сообщение о том, что данный пользователь уже подписан " +
                    "на это заведение."
    )
    @PostMapping(
            value="subscribe-to-venue",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Message> subscribeToVenue(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestById request
    ) {
        return ResponseEntity.ok(subscriptionService.subscribeToVenue(principal.getId(),
                request.getVenueId()));
    }

    @Operation(
            summary = "Отписаться от заведения по его id.",
            description = "Принимает id заведения.\nВ случае, если данный id соответствует зарегистрированному в " +
                    "системе заведению, то подписка пользователя, которому принадлежит нынешний JWT " +
                    "токен, на данное заведение отменяется. Или возвращается сообщение о том, что данный пользователь " +
                    "в данный момент не подписан на это заведение."
    )
    @PostMapping(
            value="unsubscribe-from-venue",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Message> unsubscribeFromVenue(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestById request
    ) {
        return ResponseEntity.ok(subscriptionService.unsubscribeFromVenue(principal.getId(),
                request.getVenueId()));
    }

    @Operation(
            summary = "Подписаться на заведение по его idName.",
            description = "Принимает idName заведения.\nВ случае, если данный idName соответствует зарегистрированному в " +
                    "системе заведению, то оформляется подписка пользователя, которому принадлежит нынешний JWT " +
                    "токен на данное заведение или возвращается сообщение о том, что данный пользователь уже подписан " +
                    "на это заведение."
    )
    @PostMapping(
            value="subscribe-to-venue-by-id-name",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Message> subscribeToVenueByIdName(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestByIdName request
    ) {
        return ResponseEntity.ok(subscriptionService.subscribeToVenueByIdName(principal.getId(),
                request.getVenueIdName()));
    }

    @Operation(
            summary = "Отписаться от заведения по его idName.",
            description = "Принимает idName заведения.\nВ случае, если данный Name соответствует зарегистрированному в " +
                    "системе заведению, то подписка пользователя, которому принадлежит нынешний JWT " +
                    "токен, на данное заведение отменяется. Или возвращается сообщение о том, что данный пользователь " +
                    "в данный момент не подписан на это заведение."
    )
    @PostMapping(
            value="unsubscribe-from-venue-by-id-name",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Message> unsubscribeFromVenueByIdName(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestByIdName request
    ) {
        return ResponseEntity.ok(subscriptionService.unsubscribeFromVenueByIdName(principal.getId(),
                request.getVenueIdName()));
    }

    @Operation(
            summary = "Получить информацию о заведениях, на которые подписан авторизованный пользователь.",
            description = "Возвращает список заведений, на которые подписан пользователь, с нынешним JWT токеном"
    )
    @GetMapping(
            value="get-my-subscriptions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<VenueResponse>> getAuthenticatedUsersSubscriptions(
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(subscriptionService.getUsersSubscriptionVenues(principal));
    }

    @Operation(
            summary = "Получить список пользователей, подписанных на заведение, по его id",
            description = "Возвращает список пользователей, которые подписаны на заведение с переданным id."
    )
    @GetMapping(
            value="get-venue-subscribers-by-id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserResponsePublic>> getVenueSubscribersById(
            @AuthenticationPrincipal User principal,
            @RequestParam UUID venueId
    ) {
        return ResponseEntity.ok(subscriptionService.getVenueSubscribersByVenueId(venueId));
    }

    @Operation(
            summary = "Получить список пользователей, подписанных на заведение, по его idName",
            description = "Возвращает список пользователей, которые подписаны на заведение с переданным idName."
    )
    @GetMapping(
            value="get-venue-subscribers-by-id-name",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<UserResponsePublic>> getVenueSubscribersByIdName(
            @AuthenticationPrincipal User principal,
            @RequestParam String venueIdName
    ) {
        return ResponseEntity.ok(subscriptionService.getVenueSubscribersByVenueIdName(venueIdName));
    }



}
