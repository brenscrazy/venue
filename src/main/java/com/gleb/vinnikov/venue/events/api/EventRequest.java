package com.gleb.vinnikov.venue.events.api;

import lombok.Data;

@Data
public class EventRequest {

    private final String displayName;
    private final String venueIdName;
    private final DateFields date;

}
