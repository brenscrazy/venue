package com.gleb.vinnikov.venue.subscriptions.services.impl;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.entities.UserVenueSubscription;
import com.gleb.vinnikov.venue.db.entities.Venue;
import com.gleb.vinnikov.venue.db.repos.UserVenueSubscriptionsRepo;
import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import com.gleb.vinnikov.venue.subscriptions.api.UserResponse;
import com.gleb.vinnikov.venue.subscriptions.services.SubscriptionService;
import com.gleb.vinnikov.venue.users.api.UserResponsePublic;
import com.gleb.vinnikov.venue.utils.GeneralUtils;
import com.gleb.vinnikov.venue.utils.Message;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;
import com.gleb.vinnikov.venue.venues.services.impl.VenueServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<UserResponsePublic> getVenueSubscribersByVenueId(@NonNull UUID venueId) {
        if (!venueRepo.existsById(venueId)) {
            throw new IllegalArgumentException("No venue with give id");
        }
        return GeneralUtils.mapList(
                userVenueSubscriptionsRepo.findUsersByVenueId(venueId),
                this::subscriptionToUserResponsePublic);
    }

    @Override
    public List<UserResponsePublic> getVenueSubscribersByVenueIdName(@NonNull String venueIdName) {
        if (!venueRepo.existsByIdName(venueIdName)) {
            throw new IllegalArgumentException("No venue with give idName");
        }
        return GeneralUtils.mapList(
                userVenueSubscriptionsRepo.findUsersByVenueIdName(venueIdName),
                this::subscriptionToUserResponsePublic);
    }

    @Override
    public Message unsubscribeFromVenue(@NonNull UUID userId, @NonNull UUID venueId) {
        if (!venueRepo.existsById(venueId)) {
            throw new IllegalArgumentException("No venue with give id");
        }
        return userVenueSubscriptionsRepo.findIdByUserIdAndVenueId(userId, venueId)
                .map(id -> {
                    userVenueSubscriptionsRepo.deleteById(id);
                    return new Message("Successfully unsubscribed");
                })
                .orElse(new Message("You are not subscribed."));
    }

    @Override
    public Message unsubscribeFromVenueByIdName(@NonNull UUID userId, @NonNull String venueIdName) {
        return venueRepo.findIdByIdName(venueIdName)
                .map(id -> unsubscribeFromVenue(userId, id))
                .orElseThrow(() -> new IllegalArgumentException("No venue with given idName."));
    }

    private UserResponsePublic subscriptionToUserResponsePublic(UserVenueSubscription u) {
        return new UserResponsePublic(u.getUser().getId(), u.getUser().getUsername());
    }

}
