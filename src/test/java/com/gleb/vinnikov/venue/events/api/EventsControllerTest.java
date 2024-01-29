package com.gleb.vinnikov.venue.events.api;

import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import com.gleb.vinnikov.venue.events.services.EventService;
import com.gleb.vinnikov.venue.utils.Utils;
import com.gleb.vinnikov.venue.venues.api.VenueResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
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

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static com.gleb.vinnikov.venue.utils.Utils.initControllerTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT360S")
public class EventsControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private EventService eventService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtService jwtService;
    private String createEventPath;
    private String getEventPath;
    private String getEventsPath;

    @Before
    public void init() {
        initControllerTest(jwtService, userDetailsService);
        createEventPath = Utils.buildPath(port, "create-event");
        getEventPath = Utils.buildPath(port, "get-event");
        getEventsPath = Utils.buildPath(port, "get-events");
    }

    @Test
    public void testCreateEvent() {
        when(eventService.createEvent(TEST_USER_1, TEST_EVENT_REQUEST_1)).thenReturn(TEST_EVENT_RESPONSE_1);
        createEvent(TEST_EVENT_REQUEST_1).expectStatus().isOk().expectBody(EventResponse.class)
                .isEqualTo(TEST_EVENT_RESPONSE_1);
    }

    @Test
    public void testGetEvent() {
        when(eventService.getEvent(TEST_EVENT_1.getId())).thenReturn(TEST_EVENT_RESPONSE_1);
        getEvent(TEST_EVENT_1.getId()).expectStatus().isOk().expectBody(EventResponse.class)
                .isEqualTo(TEST_EVENT_RESPONSE_1);
    }

    @Test
    public void testGetEventNotFound() {
        when(eventService.getEvent(TEST_EVENT_1.getId())).thenThrow(new NoSuchElementException());
        getEvent(TEST_EVENT_1.getId()).expectStatus().isNotFound();
    }

    @Test
    public void testGetEvents() {
        GetEventRequest request = new GetEventRequest();
        List<EventResponse> response = List.of(TEST_EVENT_RESPONSE_1, TEST_EVENT_RESPONSE_2);
        when(eventService.getEvents(request)).thenReturn(response);
        getEvents(request).expectStatus().isOk().expectBody(new ParameterizedTypeReference<List<EventResponse>>() {})
                .isEqualTo(response);
    }

    private WebTestClient.ResponseSpec createEvent(EventRequest eventRequest) {
        return Utils.performJsonHttpPostRequest(webTestClient, createEventPath, eventRequest);
    }

    private WebTestClient.ResponseSpec getEvent(UUID uuid) {
        return Utils.performJsonHttpGetRequest(webTestClient, getEventPath + "?id=" + uuid);
    }

    private WebTestClient.ResponseSpec getEvents(GetEventRequest getEventRequest) {
        return Utils.performJsonHttpPostRequest(webTestClient, getEventsPath, getEventRequest);
    }

}
