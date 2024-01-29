package com.gleb.vinnikov.venue.auth.api;

import com.gleb.vinnikov.venue.db.entities.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotNull(message = "username field is missing")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$", message = "Username should have from 4 to 29 characters. " +
            "First character must be a letter, and others can be any of these: A-Z, a-z, 0-9, _")
    private final String username;

    @NotNull(message = "email field is missing")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email is in the wrong format." +
            "Check if it is correct.")
    private final String email;

    @NotNull(message = "password field is missing")
    private final String password;

    @NotNull(message = "role field is missing")
    private final Role role;

}
