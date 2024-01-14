package com.gleb.vinnikov.venue.venues.api;

import lombok.Data;

import java.util.UUID;

@Data
public class VenueResponse {

    private final UUID id;
    private final String createdBy;
    private final String idName;
    private final String displayName;

}
