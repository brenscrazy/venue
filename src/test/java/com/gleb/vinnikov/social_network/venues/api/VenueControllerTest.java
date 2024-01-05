package com.gleb.vinnikov.social_network.venues.api;

import com.gleb.vinnikov.social_network.auth.jwt.JwtService;
import com.gleb.vinnikov.social_network.auth.services.LoginService;
import com.gleb.vinnikov.social_network.db.entities.Role;
import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.entities.Venue;
import com.gleb.vinnikov.social_network.utils.Utils;
import com.gleb.vinnikov.social_network.venues.services.VenueService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
//Context
    private String addVenuePath;
    private String getVenueByIdPath;
    private String getVenueByIdNamePath;
    private String getVenueByDisplayNamePath;

    @Before
    public void init() {
        Claims claims = new DefaultClaims();
        claims.setSubject("brenscrazy");
        claims.setExpiration(new Date(System.currentTimeMillis() + 1000000));
        when(jwtService.getClaims(any())).thenReturn(claims);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(
                new User(UUID.randomUUID(), "brenscrazy", "qwerty123", "glebmanufa@gmail.com", Role.USER));
        addVenuePath = "http://localhost:" + port + "/add-venue";
        getVenueByIdPath = "http://localhost:" + port + "/venue-by-id";
        getVenueByIdNamePath = "http://localhost:" + port + "/venue-by-id-name";
        getVenueByDisplayNamePath = "http://localhost:" + port + "/venue-by-display-name";
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(300))
                .build();
    }


    @Test
    public void addVenueTest() {
        VenueResponse venueResponse = new VenueResponse(UUID.randomUUID(), "ionoff", "ionoteka", "IONOTEKA");
        when(venueService.addVenue(any(), any())).thenReturn(venueResponse);
        addVenue(new VenueCreationData("ionoteka", "IONOTEKA")).expectStatus().isOk().expectBody(VenueResponse.class)
                .isEqualTo(venueResponse);
    }

    @Test
    public void getVenueByIdTest() {
        VenueResponse venueResponse = new VenueResponse(UUID.randomUUID(), "ionoff", "ionoteka", "IONOTEKA");
        UUID id = UUID.randomUUID();
        when(venueService.getById(id)).thenReturn(venueResponse);
        getVenueById(id).expectStatus().isOk().expectBody(VenueResponse.class).isEqualTo(venueResponse);
    }

    @Test
    public void getVenueByNameTest() {
        VenueResponse venueResponse = new VenueResponse(UUID.randomUUID(), "ionoff", "ionoteka", "IONOTEKA");
        when(venueService.getByIdName("ionoteka")).thenReturn(venueResponse);
        getVenueByName("ionoteka").expectStatus().isOk().expectBody(VenueResponse.class).isEqualTo(venueResponse);
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
