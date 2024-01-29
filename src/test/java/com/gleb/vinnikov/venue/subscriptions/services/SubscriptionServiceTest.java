package com.gleb.vinnikov.venue.subscriptions.services;

import com.gleb.vinnikov.venue.db.repos.UserVenueSubscriptionsRepo;
import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import com.gleb.vinnikov.venue.subscriptions.services.impl.SubscriptionServiceImpl;
import com.gleb.vinnikov.venue.utils.Message;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceTest {

    @Mock
    private UserVenueSubscriptionsRepo userVenueSubscriptionsRepo;
    @Mock
    private VenueRepo venueRepo;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    public void testSubscribeToVenue() {
        Message expected = new Message("Successfully subscribed");
        when(userVenueSubscriptionsRepo.existsByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(false);
        when(userVenueSubscriptionsRepo.save(any())).thenReturn(TEST_USER_VENUE_SUBSCRIPTION_1);
        assertEquals(expected, subscriptionService.subscribeToVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()));
    }

    @Test
    public void testSubscribeToVenueSubscribed() {
        Message expected = new Message("Already subscribed.");
        when(userVenueSubscriptionsRepo.existsByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(true);
        assertEquals(expected, subscriptionService.subscribeToVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()));
    }

    @Test
    public void testSubscribeToVenueByIdName() {
        Message expected = new Message("Successfully subscribed");
        when(venueRepo.findIdByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.of(TEST_VENUE_1.getId()));
        when(userVenueSubscriptionsRepo.existsByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(false);
        when(userVenueSubscriptionsRepo.save(any())).thenReturn(TEST_USER_VENUE_SUBSCRIPTION_1);
        assertEquals(expected, subscriptionService.subscribeToVenueByIdName(TEST_USER_1.getId(),
                TEST_VENUE_1.getIdName()));
    }

    @Test
    public void testSubscribeToVenueByIdNameSubscribed() {
        Message expected = new Message("Already subscribed.");
        when(venueRepo.findIdByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.of(TEST_VENUE_1.getId()));
        when(userVenueSubscriptionsRepo.existsByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(true);
        assertEquals(expected, subscriptionService.subscribeToVenueByIdName(TEST_USER_1.getId(),
                TEST_VENUE_1.getIdName()));
    }

    @Test
    public void testUnsubscribeFromVenue() {
        Message expected = new Message("Successfully unsubscribed");
        when(venueRepo.existsById(TEST_VENUE_1.getId())).thenReturn(true);
        when(userVenueSubscriptionsRepo.findIdByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(Optional.of(TEST_USER_VENUE_SUBSCRIPTION_1.getId()));
        assertEquals(expected, subscriptionService.unsubscribeFromVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()));
    }

    @Test
    public void testUnsubscribeFromVenueUnsubscribed() {
        Message expected = new Message("You are not subscribed.");
        when(venueRepo.existsById(TEST_VENUE_1.getId())).thenReturn(true);
        when(userVenueSubscriptionsRepo.findIdByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(Optional.empty());
        assertEquals(expected, subscriptionService.unsubscribeFromVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()));
    }

    @Test
    public void testUnsubscribeFromVenueByIdName() {
        Message expected = new Message("Successfully unsubscribed");
        when(venueRepo.existsById(TEST_VENUE_1.getId())).thenReturn(true);
        when(userVenueSubscriptionsRepo.findIdByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(Optional.of(TEST_USER_VENUE_SUBSCRIPTION_1.getId()));
        when(venueRepo.findIdByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.of(TEST_VENUE_1.getId()));
        assertEquals(expected, subscriptionService.unsubscribeFromVenueByIdName(TEST_USER_1.getId(),
                TEST_VENUE_1.getIdName()));
    }

    @Test
    public void testUnsubscribeFromVenueByIdNameUnsubscribed() {
        Message expected = new Message("You are not subscribed.");
        when(venueRepo.existsById(TEST_VENUE_1.getId())).thenReturn(true);
        when(userVenueSubscriptionsRepo.findIdByUserIdAndVenueId(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(Optional.empty());
        when(venueRepo.findIdByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.of(TEST_VENUE_1.getId()));
        assertEquals(expected, subscriptionService.unsubscribeFromVenueByIdName(TEST_USER_1.getId(),
                TEST_VENUE_1.getIdName()));
    }

    @Test
    public void testGetUsersSubscriptionVenues() {
        List<VenueResponse> expected = List.of(TEST_VENUE_RESPONSE_1);
        when(userVenueSubscriptionsRepo.findAll(any(Example.class))).thenReturn(
                List.of(TEST_USER_VENUE_SUBSCRIPTION_1));
        assertEquals(expected, subscriptionService.getUsersSubscriptionVenues(TEST_USER_1));
    }

    @Test
    public void testGetVenueSubscribersByVenueId() {
        when(venueRepo.existsById(TEST_VENUE_1.getId())).thenReturn(true);
        when(userVenueSubscriptionsRepo.findUsersByVenueId(TEST_VENUE_1.getId())).thenReturn(
                List.of(TEST_USER_VENUE_SUBSCRIPTION_1));
        assertEquals(List.of(TEST_USER_RESPONSE_PUBLIC_1), subscriptionService.getVenueSubscribersByVenueId(
                TEST_VENUE_1.getId()));
    }

    @Test
    public void testGetVenueSubscribersByVenueIdName() {
        when(venueRepo.existsByIdName(TEST_VENUE_1.getIdName())).thenReturn(true);
        when(userVenueSubscriptionsRepo.findUsersByVenueIdName(TEST_VENUE_1.getIdName())).thenReturn(
                List.of(TEST_USER_VENUE_SUBSCRIPTION_1));
        assertEquals(List.of(TEST_USER_RESPONSE_PUBLIC_1), subscriptionService.getVenueSubscribersByVenueIdName(
                TEST_VENUE_1.getIdName()));
    }

    @Test
    public void testVenueDoesNotExists() {
        when(venueRepo.existsByIdName(TEST_VENUE_1.getIdName())).thenReturn(false);
        when(venueRepo.existsById(TEST_VENUE_1.getId())).thenReturn(false);
        when(venueRepo.findIdByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.empty());
        when(userVenueSubscriptionsRepo.save(any())).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(IllegalArgumentException.class, () ->
                subscriptionService.subscribeToVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()));
        assertThrows(IllegalArgumentException.class, () ->
                subscriptionService.subscribeToVenueByIdName(TEST_USER_1.getId(), TEST_VENUE_1.getIdName()));
        assertThrows(IllegalArgumentException.class, () ->
                subscriptionService.unsubscribeFromVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()));
        assertThrows(IllegalArgumentException.class, () ->
                subscriptionService.unsubscribeFromVenueByIdName(TEST_USER_1.getId(), TEST_VENUE_1.getIdName()));
        assertThrows(IllegalArgumentException.class, () ->
                subscriptionService.getVenueSubscribersByVenueId(TEST_VENUE_1.getId()));
        assertThrows(IllegalArgumentException.class, () ->
                subscriptionService.getVenueSubscribersByVenueIdName(TEST_VENUE_1.getIdName()));
    }

}
