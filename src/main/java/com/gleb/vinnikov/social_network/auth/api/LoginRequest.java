package com.gleb.vinnikov.social_network.auth.api;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "username field is missing")
    private final String username;
    @NotNull(message = "password field is missing")
    private final String password;

}
