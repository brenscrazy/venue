package com.gleb.vinnikov.venue.auth.api;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginResponse {

    @NonNull
    private final String accessToken;

}
