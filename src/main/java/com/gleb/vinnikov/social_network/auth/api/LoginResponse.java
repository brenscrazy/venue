package com.gleb.vinnikov.social_network.auth.api;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginResponse {

    @NonNull
    private final String accessToken;

}
