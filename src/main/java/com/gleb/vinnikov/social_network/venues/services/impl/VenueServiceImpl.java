package com.gleb.vinnikov.social_network.venues.services.impl;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.entities.Venue;
import com.gleb.vinnikov.social_network.db.repos.VenueRepo;
import com.gleb.vinnikov.social_network.venues.api.VenueCreationData;
import com.gleb.vinnikov.social_network.venues.api.VenueResponse;
import com.gleb.vinnikov.social_network.venues.services.VenueService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepo venueRepo;

    @Override
    public VenueResponse getById(@NonNull UUID id) {
        Venue venue = handleOptional(venueRepo.findById(id)); //todo add error message
        return venueToVenueResponse(venue);
    }

    @Override
    public VenueResponse getByIdName(@NonNull String idName) {
        Venue venue = handleOptional(venueRepo.findByIdName(idName));
        return venueToVenueResponse(venue);
    }

    @Override
    public List<VenueResponse> getByDisplayName(@NonNull String displayName) {
        List<Venue> venue = venueRepo.findAllByDisplayNameOrderByIdName(displayName);
        return venue.stream().map(VenueServiceImpl::venueToVenueResponse).collect(Collectors.toList());
    }

    @Override
    public List<VenueResponse> getByNamePrefix(@NonNull String name) {
        List<Venue> venue = venueRepo
                .findAllByDisplayNameStartingWithOrIdNameStartingWithOrderByDisplayNameAscIdNameAsc(name, name);
        return venue.stream().map(VenueServiceImpl::venueToVenueResponse).collect(Collectors.toList());
    }

    @Override
    public VenueResponse addVenue(@NonNull VenueCreationData venueCreationData, @NonNull User user) {
        Venue venue = Venue.builder()
                .idName(venueCreationData.getIdName())
                .creatorUsername(user.getUsername())
                .displayName(venueCreationData.getDisplayName()).build();
        return venueToVenueResponse(venueRepo.save(venue));
    }

    private Venue handleOptional(Optional<Venue> venueResponseOptional) {
        return venueResponseOptional.orElseThrow(() -> new NoSuchElementException("no such venue"));
    }

    public static VenueResponse venueToVenueResponse(Venue venue) {
        return new VenueResponse(
                venue.getId(),
                venue.getCreatorUsername(),
                venue.getIdName(),
                venue.getDisplayName()
        );
    }

}
