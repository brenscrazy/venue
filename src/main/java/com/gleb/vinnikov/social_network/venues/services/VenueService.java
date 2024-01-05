package com.gleb.vinnikov.social_network.venues.services;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.entities.Venue;
import com.gleb.vinnikov.social_network.db.repos.VenueRepo;
import com.gleb.vinnikov.social_network.venues.api.VenueCreationData;
import com.gleb.vinnikov.social_network.venues.api.VenueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepo venueRepo;

    public VenueResponse getById(UUID id) {
        Venue venue = handleOptional(venueRepo.findById(id)); //todo add error message
        return venueToVenueResponse(venue);
    }

    public VenueResponse getByIdName(String idName) {
        Venue venue = handleOptional(venueRepo.findByIdName(idName));
        return venueToVenueResponse(venue);
    }

    public List<VenueResponse> getByDisplayName(String displayName) {
        List<Venue> venue = venueRepo.findAllByDisplayName(displayName);
        return venue.stream().map(this::venueToVenueResponse).collect(Collectors.toList());
    }

    public List<VenueResponse> getByNamePrefix(String name) {
        List<Venue> venue = venueRepo.findAllByDisplayNameOrIdNameStartingWithOrderByDisplayName(name, name);
        return venue.stream().map(this::venueToVenueResponse).collect(Collectors.toList());
    }

    public VenueResponse addVenue(VenueCreationData venueCreationData, User user) {
        Venue venue = Venue.builder()
                .idName(venueCreationData.getIdName())
                .createdBy(user)
                .displayName(venueCreationData.getDisplayName()).build();
        return venueToVenueResponse(venueRepo.save(venue));
    }

    private Venue handleOptional(Optional<Venue> venueResponseOptional) {
        return venueResponseOptional.orElseThrow(() -> new NoSuchElementException("no such venue"));
    }

    private VenueResponse venueToVenueResponse(Venue venue) {
        return new VenueResponse(
                venue.getId(),
                venue.getCreatedBy().getUsername(),
                venue.getIdName(),
                venue.getDisplayName()
        );
    }

}
