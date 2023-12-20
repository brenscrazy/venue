package com.gleb.vinnikov.social_network.venues.api;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VenueCreationData {

    @NotNull(message = "name field is missing")
    private String name;

}
