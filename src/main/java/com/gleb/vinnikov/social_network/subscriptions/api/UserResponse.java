package com.gleb.vinnikov.social_network.subscriptions.api;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private String username;

}
