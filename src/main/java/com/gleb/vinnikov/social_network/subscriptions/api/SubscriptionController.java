package com.gleb.vinnikov.social_network.subscriptions.api;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.subscriptions.services.SubscriptionService;
import com.gleb.vinnikov.social_network.utils.Message;
import com.gleb.vinnikov.social_network.venues.api.VenueResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
            Authentication principal,
            @RequestBody SubscriptionRequestById request
    ) {
        return ResponseEntity.ok(subscriptionService.subscribeToVenue(((User) principal.getPrincipal()).getId(),
                request.getVenueId()));
    }

    @PostMapping(
            value="subscribe-to-venue-by-id-name"
    )
    public ResponseEntity<Message> subscribeToVenueByIdName(
            Authentication principal,
            @RequestBody SubscriptionRequestByIdName request
    ) {
        return ResponseEntity.ok(subscriptionService.subscribeToVenueByIdName(((User) principal.getPrincipal()).getId(),
                request.getVenueIdName()));
    }

    @GetMapping(
            value="get-my-subscriptions"
    )
    public ResponseEntity<List<VenueResponse>> getAuthenticatedUsersSubscriptions(
            Authentication principal
    ) {
        return ResponseEntity.ok(subscriptionService.getUsersSubscriptionVenues((User) principal.getPrincipal()));
    }

    @GetMapping(
            value="get-venue-subscribers-by-id"
    )
    public ResponseEntity<List<UserResponse>> getVenueSubscribers(
            Authentication principal,
            @RequestParam UUID venueId
    ) {
        return ResponseEntity.ok(subscriptionService.getVenueSubscribersByVenueId(venueId));
    }



}
