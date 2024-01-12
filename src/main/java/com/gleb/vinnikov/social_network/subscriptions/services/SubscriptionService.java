package com.gleb.vinnikov.social_network.subscriptions.services;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.subscriptions.api.UserResponse;
import com.gleb.vinnikov.social_network.utils.Message;
import com.gleb.vinnikov.social_network.venues.api.VenueResponse;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {

    Message subscribeToVenue(@NonNull UUID userId, @NonNull UUID venueId);

    Message subscribeToVenueByIdName(@NonNull UUID userId, @NonNull String venueIdName);

    List<VenueResponse> getUsersSubscriptionVenues(@NonNull User user);

    List<UserResponse> getVenueSubscribersByVenueId(@NonNull UUID venueId);

    List<UserResponse> getVenueSubscribersByVenueIdName(@NonNull String venueIdName);

}
