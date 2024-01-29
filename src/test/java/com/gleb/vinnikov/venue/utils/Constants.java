package com.gleb.vinnikov.venue.utils;

import com.gleb.vinnikov.venue.db.entities.*;
import com.gleb.vinnikov.venue.events.api.DateFields;
import com.gleb.vinnikov.venue.events.api.EventRequest;
import com.gleb.vinnikov.venue.events.api.EventResponse;
import com.gleb.vinnikov.venue.subscriptions.api.SubscriptionRequestById;
import com.gleb.vinnikov.venue.subscriptions.api.SubscriptionRequestByIdName;
import com.gleb.vinnikov.venue.users.api.ChangeUserInfoRequest;
import com.gleb.vinnikov.venue.users.api.UserResponsePublic;
import com.gleb.vinnikov.venue.venues.api.VenueCreationData;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class Constants {

    private Constants(){}

    public static final String LOCALHOST = "http://localhost:";
    public static final UUID TEST_ID_1 = UUID.randomUUID();
    public static final UUID TEST_ID_2 = UUID.randomUUID();
    public static final UUID TEST_ID_3 = UUID.randomUUID();
    public static final UUID TEST_ID_4 = UUID.randomUUID();
    public static final UUID TEST_ID_5 = UUID.randomUUID();
    public static final User TEST_USER_1 = new User(TEST_ID_1, "user1", "password1", "email@mail.ru", Role.USER);
    public static final UserResponsePublic TEST_USER_RESPONSE_PUBLIC_1 = new UserResponsePublic(TEST_USER_1.getId(),
            TEST_USER_1.getUsername());
    public static final ChangeUserInfoRequest TEST_CHANGE_USER_INFO_REQUEST = new ChangeUserInfoRequest("newUsername",
            "new@email.ru");
    public static final ChangeUserInfoRequest TEST_CHANGE_USER_INFO_REQUEST_USERNAME_ONLY = new ChangeUserInfoRequest(
            "newUsername", null);
    public static final ChangeUserInfoRequest TEST_CHANGE_USER_INFO_REQUEST_EMAIL_ONLY = new ChangeUserInfoRequest(
            null, "new@email.com");

    public final static Venue TEST_VENUE_1 = new Venue(UUID.randomUUID(), Constants.TEST_USER_1, "venue1", "Venue");
    public final static Venue TEST_VENUE_2 = new Venue(UUID.randomUUID(), Constants.TEST_USER_1, "venue2", "Venue");
    public final static VenueResponse TEST_VENUE_RESPONSE_1 = new VenueResponse(TEST_VENUE_1.getId(),
            Constants.TEST_USER_1.getUsername(), "venue1", "Venue");
    public final static VenueResponse TEST_VENUE_RESPONSE_2 = new VenueResponse(TEST_VENUE_1.getId(),
            Constants.TEST_USER_1.getUsername(), "venue2", "Venue");
    public final static VenueCreationData TEST_VENUE_CREATION_DATA = new VenueCreationData(TEST_VENUE_1.getIdName(),
            TEST_VENUE_1.getDisplayName());

    public final static EventRequest TEST_EVENT_REQUEST_1 = new EventRequest("Event", TEST_VENUE_1.getIdName(),
            new DateFields(Timestamp.from(Instant.now())));
    public final static Event TEST_EVENT_1 = new Event(UUID.randomUUID(), TEST_VENUE_1,
            TEST_EVENT_REQUEST_1.getDate().toTimestamp(), TEST_EVENT_REQUEST_1.getDisplayName());
    public final static Event TEST_EVENT_2 = new Event(UUID.randomUUID(), TEST_VENUE_1,
            TEST_EVENT_REQUEST_1.getDate().toTimestamp(), "Event 2");
    public final static EventResponse TEST_EVENT_RESPONSE_1 = new EventResponse(TEST_EVENT_1.getId(),
            TEST_EVENT_REQUEST_1.getDisplayName(), TEST_EVENT_REQUEST_1.getVenueIdName(),
            TEST_EVENT_REQUEST_1.getDate());
    public final static EventResponse TEST_EVENT_RESPONSE_2 = new EventResponse(TEST_EVENT_2.getId(),
            TEST_EVENT_2.getDisplayName(), TEST_VENUE_1.getIdName(), TEST_EVENT_REQUEST_1.getDate());

    public final static UserVenueSubscription TEST_USER_VENUE_SUBSCRIPTION_1 = new UserVenueSubscription(
            UUID.randomUUID(), TEST_USER_1, TEST_VENUE_1);
    public final static UserVenueSubscription TEST_USER_VENUE_SUBSCRIPTION_USER_ONLY = UserVenueSubscription.builder()
            .id(TEST_USER_VENUE_SUBSCRIPTION_1.getId()).user(TEST_USER_1).build();
    public final static SubscriptionRequestById TEST_SUBSCRIPTIONS_REQUEST_BY_ID_1 =
            new SubscriptionRequestById(TEST_VENUE_1.getId());
    public final static SubscriptionRequestByIdName TEST_SUBSCRIPTIONS_REQUEST_BY_ID_NAME_1 =
            new SubscriptionRequestByIdName(TEST_VENUE_1.getIdName());

}
