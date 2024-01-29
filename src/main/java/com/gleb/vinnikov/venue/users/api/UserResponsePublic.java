package com.gleb.vinnikov.venue.users.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UserResponsePublic {

    private UUID id;
    private String username;

}
