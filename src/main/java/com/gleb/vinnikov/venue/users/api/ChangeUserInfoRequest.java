package com.gleb.vinnikov.venue.users.api;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeUserInfoRequest {

    @Nullable
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]{4,29}$", message = "Username should have from 4 to 29 characters. " +
            "First character must be a letter, and others can be any of these: A-Z, a-z, 0-9, _")
    private String newUsername;

    @Nullable
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email is in the wrong format." +
            "Check if it is correct.")
    private String newEmail;

}
