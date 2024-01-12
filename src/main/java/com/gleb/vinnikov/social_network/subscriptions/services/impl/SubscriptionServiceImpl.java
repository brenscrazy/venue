package com.gleb.vinnikov.social_network.subscriptions.services.impl;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.entities.UserVenueSubscription;
import com.gleb.vinnikov.social_network.db.entities.Venue;
import com.gleb.vinnikov.social_network.db.repos.UserVenueSubscriptionsRepo;
import com.gleb.vinnikov.social_network.db.repos.VenueRepo;
import com.gleb.vinnikov.social_network.subscriptions.api.UserResponse;
import com.gleb.vinnikov.social_network.subscriptions.services.SubscriptionService;
import com.gleb.vinnikov.social_network.utils.GeneralUtils;
import com.gleb.vinnikov.social_network.utils.Message;
import com.gleb.vinnikov.social_network.venues.api.VenueResponse;
import com.gleb.vinnikov.social_network.venues.services.impl.VenueServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserVenueSubscriptionsRepo userVenueSubscriptionsRepo;
    private final VenueRepo venueRepo;

    @Override
    public Message subscribeToVenue(@NonNull UUID userId, @NonNull UUID venueId) {
        if (userVenueSubscriptionsRepo.existsByUserIdAndVenueId(userId, venueId)) {
            return new Message("Already subscribed.");
        }
        try {
            UserVenueSubscription userVenueSubscription = new UserVenueSubscription(userId, venueId);
            userVenueSubscriptionsRepo.save(userVenueSubscription);
            return new Message("Successfully subscribed");
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No venue with given id.", e);
        }
    }

    @Override
    public Message subscribeToVenueByIdName(@NonNull UUID userId, @NonNull String venueIdName) {
        UUID venueId = venueRepo.findIdByIdName(venueIdName).orElseThrow(
                () -> new IllegalArgumentException("No venue with given idName."));
        return subscribeToVenue(userId, venueId);
    }

    @Override
    public List<VenueResponse> getUsersSubscriptionVenues(@NonNull User user) {
        return userVenueSubscriptionsRepo.findAll(Example.of(new UserVenueSubscription(user))).stream()
                .map(UserVenueSubscription::getVenue)
                .map(VenueServiceImpl::venueToVenueResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getVenueSubscribersByVenueId(@NonNull UUID venueId) {
        return GeneralUtils.mapList(
                userVenueSubscriptionsRepo.findUsersByVenueId(venueId),
                u -> new UserResponse(u.getUser().getId(), u.getUser().getUsername()));
    }

    @Override
    public List<UserResponse> getVenueSubscribersByVenueIdName(@NonNull String venueIdName) {
        return GeneralUtils.mapList(
                userVenueSubscriptionsRepo.findUsersByVenueIdName(venueIdName),
                u -> new UserResponse(u.getUser().getId(), u.getUser().getUsername()));
    }

}
