package com.gleb.vinnikov.venue.events.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetEventRequest {

    @Parameter(
            description = "Если указан, то возвращаются только события, происходящие в заведении, с переданным idName.")
    private String venueIdName;
    @Parameter(
            description = "Если указан, то возвращаются только события, происходящие после этой даты.")
    private DateFields after = new DateFields(1900, 1, 1, 0, 0);
    @Parameter(
            description = "Если указан, то возвращаются только события, происходящие до этой даты.")
    private DateFields before = new DateFields(3000, 1, 1, 0, 0);

    public GetEventRequest(String venueIdName) {
        this.venueIdName = venueIdName;
    }

}
