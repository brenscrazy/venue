package com.gleb.vinnikov.venue.subscriptions.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String username;

}
