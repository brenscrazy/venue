package com.gleb.vinnikov.social_network.venues.api;

import com.gleb.vinnikov.social_network.auth.jwt.JwtService;
import com.gleb.vinnikov.social_network.db.entities.Role;
import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.utils.Utils;
import com.gleb.vinnikov.social_network.venues.services.VenueService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
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
        Claims claims = new DefaultClaims();
        claims.setSubject("brenscrazy");
        claims.setExpiration(new Date(System.currentTimeMillis() + 1000000));
        when(jwtService.getClaims(any())).thenReturn(claims);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(
                new User(UUID.randomUUID(), "brenscrazy", "qwerty123", "glebmanufa@gmail.com", Role.USER));
        addVenuePath = Utils.buildPath(port, "add-venue");
        getVenueByIdPath = Utils.buildPath(port, "venue-by-id");
        getVenueByIdNamePath = Utils.buildPath(port, "venue-by-id-name");
        getVenueByDisplayNamePath = Utils.buildPath(port, "venue-by-display-name");
        getVenueByNamePrefixPath = Utils.buildPath(port, "venue-by-display-name-prefix");
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(300))
                .build();
    }


    @Test
    public void addVenueTest() {
        VenueResponse venueResponse = new VenueResponse(UUID.randomUUID(), "ionoff", "IONOTEKA", "IONOTEKA");
        when(venueService.addVenue(any(), any())).thenReturn(venueResponse);
        addVenue(new VenueCreationData("IONOTEKA", "IONOTEKA")).expectStatus().isOk().expectBody(VenueResponse.class)
                .isEqualTo(venueResponse);
    }

    @Test
    public void getVenueByIdTest() {
        VenueResponse venueResponse = new VenueResponse(UUID.randomUUID(), "ionoff", "IONOTEKA", "IONOTEKA");
        UUID id = UUID.randomUUID();
        when(venueService.getById(id)).thenReturn(venueResponse);
        getVenueById(id).expectStatus().isOk().expectBody(VenueResponse.class).isEqualTo(venueResponse);
    }

    @Test
    public void getVenueByIdNameTest() {
        VenueResponse venueResponse = new VenueResponse(UUID.randomUUID(), "ionoff", "IONOTEKA", "IONOTEKA");
        when(venueService.getByIdName("IONOTEKA")).thenReturn(venueResponse);
        getVenueByName("IONOTEKA").expectStatus().isOk().expectBody(VenueResponse.class).isEqualTo(venueResponse);
    }

    @Test
    public void getVenueByDisplayNameTest() {
        List<VenueResponse> venueResponse = List.of(
                new VenueResponse(UUID.randomUUID(), "ionoff", "a", "IONOTEKA"),
                new VenueResponse(UUID.randomUUID(), "ionoff", "b", "IONOTEKA"));
        when(venueService.getByDisplayName("IONOTEKA")).thenReturn(venueResponse);
        getVenueByDisplayName("IONOTEKA").expectStatus().isOk().expectBody(
                new ParameterizedTypeReference<List<VenueResponse>>() {}).isEqualTo(venueResponse);
    }

    @Test
    public void getVenueByNamePrefixTest() {
        List<VenueResponse> venueResponse = List.of(
                new VenueResponse(UUID.randomUUID(), "ionoff", "a", "aa"),
                new VenueResponse(UUID.randomUUID(), "ionoff", "bb", "aa"),
                new VenueResponse(UUID.randomUUID(), "ionoff", "ab", "IONOTEKA"));
        when(venueService.getByNamePrefix("a")).thenReturn(venueResponse);
        getVenueByNamePrefix("a").expectStatus().isOk().expectBody(
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
