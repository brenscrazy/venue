package com.gleb.vinnikov.venue.utils;

import com.gleb.vinnikov.venue.auth.api.LoginResponse;
import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

import static com.gleb.vinnikov.venue.utils.Constants.TEST_USER_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class Utils {

    private Utils(){}

    public static <T> WebTestClient.ResponseSpec performJsonHttpPostRequest(WebTestClient webTestClient, String path,
                                                                            T request) {
        return webTestClient.post().uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer token")
                .body(Mono.just(request), LoginResponse.class)
                .exchange();
    }

    public static WebTestClient.ResponseSpec performJsonHttpGetRequest(WebTestClient webTestClient, String path,
                                                                           Object... parameters) {
        return webTestClient.get().uri(path, parameters)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, "Bearer token")
                .exchange();
    }

    public static <T> WebTestClient.ResponseSpec performJsonHttpPostRequestNoToken(WebTestClient webTestClient,
                                                                                   String path,
                                                                                   T request) {
        return webTestClient.post().uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), LoginResponse.class)
                .exchange();
    }

    public static String buildPath(int port, String endpointName) {
        return Constants.LOCALHOST + port + "/" + endpointName;
    }

    public static void initControllerTest(JwtService jwtService, UserDetailsService userDetailsService) {
        Claims claims = new DefaultClaims();
        claims.setSubject(TEST_USER_1.getUsername());
        claims.setExpiration(new Date(System.currentTimeMillis() + 1000000));
        when(jwtService.getClaims(any())).thenReturn(claims);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(TEST_USER_1);
    }

}
