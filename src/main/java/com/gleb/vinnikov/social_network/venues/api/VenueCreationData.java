package com.gleb.vinnikov.social_network.venues.api;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueCreationData {

    @NotNull(message = "idName field is missing")
    private String idName;
    @NotNull(message = "displayName field is missing")
    private String displayName;

}
