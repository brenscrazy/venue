package com.gleb.vinnikov.venue.utils;

import com.gleb.vinnikov.venue.auth.api.LoginResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class Utils {

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

}
