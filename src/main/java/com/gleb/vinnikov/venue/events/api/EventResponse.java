package com.gleb.vinnikov.venue.events.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class EventResponse {

    private final UUID id;
    private final String displayName;
    private final String takesPlaceAt;
    private final DateFields date;

}
