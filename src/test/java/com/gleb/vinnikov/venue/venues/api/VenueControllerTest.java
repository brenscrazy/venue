package com.gleb.vinnikov.venue.venues.api;

import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import com.gleb.vinnikov.venue.utils.Utils;
import com.gleb.vinnikov.venue.venues.services.VenueService;
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
import java.util.*;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static com.gleb.vinnikov.venue.utils.Utils.initControllerTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT360S")
public class VenueControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private VenueService venueService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtService jwtService;
    private String addVenuePath;
    private String getVenueByIdPath;
    private String getVenueByIdNamePath;
    private String getVenueByDisplayNamePath;
    private String getVenueByNamePrefixPath;

    @Before
    public void init() {
        initControllerTest(jwtService, userDetailsService);
        addVenuePath = Utils.buildPath(port, "add-venue");
        getVenueByIdPath = Utils.buildPath(port, "venue-by-id");
        getVenueByIdNamePath = Utils.buildPath(port, "venue-by-id-name");
        getVenueByDisplayNamePath = Utils.buildPath(port, "venue-by-display-name");
        getVenueByNamePrefixPath = Utils.buildPath(port, "venue-by-display-name-prefix");
    }


    @Test
    public void addVenueTest() {
        when(venueService.addVenue(any(), any())).thenReturn(TEST_VENUE_RESPONSE_1);
        addVenue(TEST_VENUE_CREATION_DATA).expectStatus().isOk().expectBody(VenueResponse.class)
                .isEqualTo(TEST_VENUE_RESPONSE_1);
    }

    @Test
    public void getVenueByIdTest() {
        UUID id = UUID.randomUUID();
        when(venueService.getById(id)).thenReturn(TEST_VENUE_RESPONSE_1);
        getVenueById(id).expectStatus().isOk().expectBody(VenueResponse.class).isEqualTo(TEST_VENUE_RESPONSE_1);
    }

    @Test
    public void getVenueByIdNameTest() {
        when(venueService.getByIdName(TEST_VENUE_RESPONSE_1.getIdName())).thenReturn(TEST_VENUE_RESPONSE_1);
        getVenueByName(TEST_VENUE_RESPONSE_1.getIdName()).expectStatus().isOk().expectBody(VenueResponse.class)
                .isEqualTo(TEST_VENUE_RESPONSE_1);
    }

    @Test
    public void getVenueByDisplayNameTest() {
        List<VenueResponse> venueResponse = List.of(TEST_VENUE_RESPONSE_1, TEST_VENUE_RESPONSE_2);
        when(venueService.getByDisplayName(TEST_VENUE_RESPONSE_1.getDisplayName())).thenReturn(venueResponse);
        getVenueByDisplayName(TEST_VENUE_RESPONSE_1.getDisplayName()).expectStatus().isOk().expectBody(
                new ParameterizedTypeReference<List<VenueResponse>>() {}).isEqualTo(venueResponse);
    }

    @Test
    public void getVenueByNamePrefixTest() {
        List<VenueResponse> venueResponse = List.of(TEST_VENUE_RESPONSE_1, TEST_VENUE_RESPONSE_2);
        when(venueService.getByNamePrefix("ve")).thenReturn(venueResponse);
        getVenueByNamePrefix("ve").expectStatus().isOk().expectBody(
                new ParameterizedTypeReference<List<VenueResponse>>() {}).isEqualTo(venueResponse);
    }

    @Test
    public void venueNotFoundTest() {
        when(venueService.getById(any())).thenThrow(NoSuchElementException.class);
        getVenueById(UUID.randomUUID()).expectStatus().isNotFound();
        when(venueService.getByIdName(any())).thenThrow(NoSuchElementException.class);
        getVenueByName("name").expectStatus().isNotFound();
    }

    private WebTestClient.ResponseSpec getVenueByNamePrefix(String name) {
        return Utils.performJsonHttpGetRequest(webTestClient, getVenueByNamePrefixPath + "?name_prefix=" + name);
    }

    private WebTestClient.ResponseSpec getVenueByDisplayName(String name) {
        return Utils.performJsonHttpGetRequest(webTestClient, getVenueByDisplayNamePath + "?name=" + name);
    }

    private WebTestClient.ResponseSpec addVenue(VenueCreationData venueCreationData) {
        return Utils.performJsonHttpPostRequest(webTestClient, addVenuePath, venueCreationData);
    }

    private WebTestClient.ResponseSpec getVenueById(UUID uuid) {
        return Utils.performJsonHttpGetRequest(webTestClient, getVenueByIdPath + "?id=" + uuid);
    }

    private WebTestClient.ResponseSpec getVenueByName(String name) {
        return Utils.performJsonHttpGetRequest(webTestClient, getVenueByIdNamePath + "?name=" + name);
    }



}
