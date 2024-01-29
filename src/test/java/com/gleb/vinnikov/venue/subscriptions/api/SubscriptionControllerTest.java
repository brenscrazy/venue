package com.gleb.vinnikov.venue.subscriptions.api;

import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import com.gleb.vinnikov.venue.events.api.EventResponse;
import com.gleb.vinnikov.venue.subscriptions.api.SubscriptionRequestById;
import com.gleb.vinnikov.venue.subscriptions.api.SubscriptionRequestByIdName;
import com.gleb.vinnikov.venue.subscriptions.api.UserResponse;
import com.gleb.vinnikov.venue.subscriptions.services.SubscriptionService;
import com.gleb.vinnikov.venue.users.api.UserResponsePublic;
import com.gleb.vinnikov.venue.utils.Message;
import com.gleb.vinnikov.venue.utils.Utils;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static com.gleb.vinnikov.venue.utils.Utils.buildPath;
import static com.gleb.vinnikov.venue.utils.Utils.initControllerTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT360S")
public class SubscriptionControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private SubscriptionService subscriptionService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtService jwtService;
    private String subscribeToVenuePath;
    private String unsubscribeFromVenuePath;
    private String subscribeToVenueByIdNamePath;
    private String unsubscribeFromVenueByIdPathPath;
    private String getAuthenticatedUsersSubscriptionsPath;
    private String getVenueSubscribersByIdPath;
    private String getVenueSubscribersByIdNamePath;

    @Before
    public void init() {
        initControllerTest(jwtService, userDetailsService);
        subscribeToVenuePath = buildPath(port, "subscribe-to-venue");
        unsubscribeFromVenuePath = buildPath(port, "unsubscribe-from-venue");
        subscribeToVenueByIdNamePath = buildPath(port, "subscribe-to-venue-by-id-name");
        unsubscribeFromVenueByIdPathPath = buildPath(port, "unsubscribe-from-venue-by-id-name");
        getAuthenticatedUsersSubscriptionsPath = buildPath(port, "get-my-subscriptions");
        getVenueSubscribersByIdPath = buildPath(port, "get-venue-subscribers-by-id");
        getVenueSubscribersByIdNamePath = buildPath(port, "get-venue-subscribers-by-id-name");
    }

    @Test
    public void testSubscribeToVenue() {
        when(subscriptionService.subscribeToVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(new Message("ok"));
        subscribeToVenue(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_1).expectStatus().isOk().expectBody(Message.class);
    }

    @Test
    public void testSubscribeToVenueDoesNotExists() {
        when(subscriptionService.subscribeToVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenThrow(new IllegalArgumentException());
        subscribeToVenue(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_1).expectStatus().isBadRequest();
    }

    @Test
    public void testSubscribeToVenueByIdName() {
        when(subscriptionService.subscribeToVenueByIdName(TEST_USER_1.getId(), TEST_VENUE_1.getIdName()))
                .thenReturn(new Message("ok"));
        subscribeToVenueByIdName(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_NAME_1).expectStatus().isOk()
                .expectBody(Message.class);
    }

    @Test
    public void testSubscribeToVenueByIdNameDoesNotExists() {
        when(subscriptionService.subscribeToVenueByIdName(TEST_USER_1.getId(), TEST_VENUE_1.getIdName()))
                .thenThrow(new IllegalArgumentException());
        subscribeToVenueByIdName(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_NAME_1).expectStatus().isBadRequest();
    }

    @Test
    public void testUnsubscribeFromVenue() {
        when(subscriptionService.unsubscribeFromVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenReturn(new Message("ok"));
        unsubscribeFromVenue(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_1).expectStatus().isOk().expectBody(Message.class);
    }

    @Test
    public void testUnsubscribeFromVenueNotFound() {
        when(subscriptionService.unsubscribeFromVenue(TEST_USER_1.getId(), TEST_VENUE_1.getId()))
                .thenThrow(new IllegalArgumentException());
        unsubscribeFromVenue(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_1).expectStatus().isBadRequest();
    }

    @Test
    public void testUnsubscribeFromVenueByIdName() {
        when(subscriptionService.unsubscribeFromVenueByIdName(TEST_USER_1.getId(), TEST_VENUE_1.getIdName()))
                .thenReturn(new Message("ok"));
        unsubscribeFromVenueById(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_NAME_1).expectStatus().isOk()
                .expectBody(Message.class);
    }

    @Test
    public void testUnsubscribeFromVenueByIdNameNotFound() {
        when(subscriptionService.unsubscribeFromVenueByIdName(TEST_USER_1.getId(), TEST_VENUE_1.getIdName()))
                .thenThrow(new IllegalArgumentException());
        unsubscribeFromVenueById(TEST_SUBSCRIPTIONS_REQUEST_BY_ID_NAME_1).expectStatus().isBadRequest();
    }

    @Test
    public void testGetAuthenticatedUsersSubscriptions() {
        List<VenueResponse> response = List.of(TEST_VENUE_RESPONSE_1, TEST_VENUE_RESPONSE_2);
        when(subscriptionService.getUsersSubscriptionVenues(TEST_USER_1)).thenReturn(response);
        getAuthenticatedUsersSubscriptions().expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<VenueResponse>>() {}).isEqualTo(response);
    }

    @Test
    public void testGetVenueSubscribersById() {
        List<UserResponsePublic> response = List.of(TEST_USER_RESPONSE_PUBLIC_1);
        when(subscriptionService.getVenueSubscribersByVenueId(TEST_VENUE_1.getId())).thenReturn(response);
        getVenueSubscribersById(TEST_VENUE_1.getId()).expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<UserResponsePublic>>() {}).isEqualTo(response);
    }

    @Test
    public void testGetVenueSubscribersByIdVenueNotFound() {
        when(subscriptionService.getVenueSubscribersByVenueId(TEST_VENUE_1.getId()))
                .thenThrow(new IllegalArgumentException());
        getVenueSubscribersById(TEST_VENUE_1.getId()).expectStatus().isBadRequest();
    }

    @Test
    public void testGetVenueSubscribersByIdName() {
        List<UserResponsePublic> response = List.of(TEST_USER_RESPONSE_PUBLIC_1);
        when(subscriptionService.getVenueSubscribersByVenueIdName(TEST_VENUE_1.getIdName())).thenReturn(response);
        getVenueSubscribersByIdName(TEST_VENUE_1.getIdName()).expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<UserResponsePublic>>() {}).isEqualTo(response);
    }

    @Test
    public void testGetVenueSubscribersByIdNameVenueNotFound() {
        when(subscriptionService.getVenueSubscribersByVenueIdName(TEST_VENUE_1.getIdName()))
                .thenThrow(new IllegalArgumentException());
        getVenueSubscribersByIdName(TEST_VENUE_1.getIdName()).expectStatus().isBadRequest();
    }

    private WebTestClient.ResponseSpec subscribeToVenue(SubscriptionRequestById subscriptionRequestById) {
        return Utils.performJsonHttpPostRequest(webTestClient, subscribeToVenuePath, subscriptionRequestById);
    }
    private WebTestClient.ResponseSpec unsubscribeFromVenue(SubscriptionRequestById subscriptionRequestById) {
        return Utils.performJsonHttpPostRequest(webTestClient, unsubscribeFromVenuePath, subscriptionRequestById);
    }
    private WebTestClient.ResponseSpec subscribeToVenueByIdName(
            SubscriptionRequestByIdName subscriptionRequestByIdName) {
        return Utils.performJsonHttpPostRequest(webTestClient, subscribeToVenueByIdNamePath,
                subscriptionRequestByIdName);
    }
    private WebTestClient.ResponseSpec unsubscribeFromVenueById(
            SubscriptionRequestByIdName subscriptionRequestByIdName) {
        return Utils.performJsonHttpPostRequest(webTestClient, unsubscribeFromVenueByIdPathPath,
                subscriptionRequestByIdName);
    }
    private WebTestClient.ResponseSpec getAuthenticatedUsersSubscriptions() {
        return Utils.performJsonHttpGetRequest(webTestClient, getAuthenticatedUsersSubscriptionsPath);
    }
    private WebTestClient.ResponseSpec getVenueSubscribersById(UUID venueId) {
        return Utils.performJsonHttpGetRequest(webTestClient, getVenueSubscribersByIdPath + "?venueId=" + venueId);
    }
    private WebTestClient.ResponseSpec getVenueSubscribersByIdName(String venueIdName) {
        return Utils.performJsonHttpGetRequest(webTestClient, getVenueSubscribersByIdNamePath + "?venueIdName="
                + venueIdName);
    }




}
