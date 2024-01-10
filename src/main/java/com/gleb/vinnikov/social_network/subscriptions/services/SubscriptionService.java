package com.gleb.vinnikov.social_network.subscriptions.services;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.entities.UserVenueSubscription;
import com.gleb.vinnikov.social_network.db.entities.Venue;
import com.gleb.vinnikov.social_network.db.repos.UserVenueSubscriptionsRepo;
import com.gleb.vinnikov.social_network.db.repos.VenueRepo;
import com.gleb.vinnikov.social_network.subscriptions.api.UserResponse;
import com.gleb.vinnikov.social_network.utils.IdOnly;
import com.gleb.vinnikov.social_network.utils.Message;
import com.gleb.vinnikov.social_network.venues.api.VenueResponse;
import com.gleb.vinnikov.social_network.venues.services.VenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final UserVenueSubscriptionsRepo userVenueSubscriptionsRepo;
    private final VenueRepo venueRepo;

    public Message subscribeToVenue(UUID userId, UUID venueId) {
        UserVenueSubscription<IdOnly, IdOnly> userVenueSubscription = new UserVenueSubscription<>(new IdOnly(userId),
                new IdOnly(venueId));
        if (userVenueSubscriptionsRepo.existsById(userVenueSubscription.getIds())) {
            return new Message("Already subscribed.");
        }
        try {
            userVenueSubscriptionsRepo.save(userVenueSubscription);
            return new Message("Successfully subscribed");
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No venue with given id.", e);
        }
    }

    public Message subscribeToVenueByIdName(UUID userId, String venueIdName) {
        UUID venueId = venueRepo.findIdByIdName(venueIdName).orElseThrow(
                () -> new IllegalArgumentException("No venue with given idName."));
        return subscribeToVenue(userId, venueId);
    }

    public List<VenueResponse> getUsersSubscriptionVenues(User user) {
        return userVenueSubscriptionsRepo.findAll(Example.of(new UserVenueSubscription(user.getId()))).stream()
                .map(UserVenueSubscription::getVenue)
                .map(VenueService::venueToVenueResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getVenueSubscribers(UUID venueId) {
        return null;
    }
}
