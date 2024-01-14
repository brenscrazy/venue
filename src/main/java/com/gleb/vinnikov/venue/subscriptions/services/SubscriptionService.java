package com.gleb.vinnikov.venue.subscriptions.services;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.subscriptions.api.UserResponse;
import com.gleb.vinnikov.venue.utils.Message;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    Message subscribeToVenue(@NonNull UUID userId, @NonNull UUID venueId);

    Message subscribeToVenueByIdName(@NonNull UUID userId, @NonNull String venueIdName);

    List<VenueResponse> getUsersSubscriptionVenues(@NonNull User user);

    List<UserResponse> getVenueSubscribersByVenueId(@NonNull UUID venueId);

    List<UserResponse> getVenueSubscribersByVenueIdName(@NonNull String venueIdName);

    Message unsubscribeFromVenue(@NonNull UUID userId, @NonNull UUID venueId);

    Message unsubscribeFromVenueByIdName(@NonNull UUID userId, @NonNull String venueIdName);
}
