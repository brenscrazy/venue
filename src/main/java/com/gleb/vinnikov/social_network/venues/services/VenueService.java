package com.gleb.vinnikov.social_network.venues.services;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.venues.api.VenueCreationData;
import com.gleb.vinnikov.social_network.venues.api.VenueResponse;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface VenueService {

    VenueResponse getById(@NonNull UUID id);

    VenueResponse getByIdName(@NonNull String idName);

    List<VenueResponse> getByDisplayName(@NonNull String displayName);

    List<VenueResponse> getByNamePrefix(@NonNull String name);

    VenueResponse addVenue(@NonNull VenueCreationData venueCreationData, @NonNull User user);

}
