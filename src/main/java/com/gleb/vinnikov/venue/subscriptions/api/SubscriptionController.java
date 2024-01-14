package com.gleb.vinnikov.venue.subscriptions.api;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.subscriptions.services.SubscriptionService;
import com.gleb.vinnikov.venue.utils.Message;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Methods to add/get/update/delete subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping(
            value="subscribe-to-venue"
    )
    public ResponseEntity<Message> subscribeToVenue(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestById request
    ) {
        return ResponseEntity.ok(subscriptionService.subscribeToVenue(principal.getId(),
                request.getVenueId()));
    }

    @PostMapping(
            value="unsubscribe-from-venue"
    )
    public ResponseEntity<Message> unsubscribeFromVenue(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestById request
    ) {
        return ResponseEntity.ok(subscriptionService.unsubscribeFromVenue(principal.getId(),
                request.getVenueId()));
    }

    @PostMapping(
            value="subscribe-to-venue-by-id-name"
    )
    public ResponseEntity<Message> subscribeToVenueByIdName(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestByIdName request
    ) {
        return ResponseEntity.ok(subscriptionService.subscribeToVenueByIdName(principal.getId(),
                request.getVenueIdName()));
    }

    @PostMapping(
            value="unsubscribe-from-venue-by-id-name"
    )
    public ResponseEntity<Message> unsubscribeFromVenue(
            @AuthenticationPrincipal User principal,
            @RequestBody SubscriptionRequestByIdName request
    ) {
        return ResponseEntity.ok(subscriptionService.unsubscribeFromVenueByIdName(principal.getId(),
                request.getVenueIdName()));
    }

    @GetMapping(
            value="get-my-subscriptions"
    )
    public ResponseEntity<List<VenueResponse>> getAuthenticatedUsersSubscriptions(
            @AuthenticationPrincipal User principal
    ) {
        return ResponseEntity.ok(subscriptionService.getUsersSubscriptionVenues(principal));
    }

    @GetMapping(
            value="get-venue-subscribers-by-id"
    )
    public ResponseEntity<List<UserResponse>> getVenueSubscribersById(
            @AuthenticationPrincipal User principal,
            @RequestParam UUID venueId
    ) {
        return ResponseEntity.ok(subscriptionService.getVenueSubscribersByVenueId(venueId));
    }

    @GetMapping(
            value="get-venue-subscribers-by-id-name"
    )
    public ResponseEntity<List<UserResponse>> getVenueSubscribersById(
            @AuthenticationPrincipal User principal,
            @RequestParam String venueIdName
    ) {
        return ResponseEntity.ok(subscriptionService.getVenueSubscribersByVenueIdName(venueIdName));
    }



}
