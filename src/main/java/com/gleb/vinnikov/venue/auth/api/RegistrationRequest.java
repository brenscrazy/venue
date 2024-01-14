package com.gleb.vinnikov.venue.auth.api;

import com.gleb.vinnikov.venue.db.entities.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotNull(message = "username field is missing")
    private final String username;
    @NotNull(message = "email field is missing")
    private final String email;
    @NotNull(message = "password field is missing")
    private final String password;
    @NotNull(message = "role field is missing")
    private final Role role;

}
