package com.gleb.vinnikov.venue.subscriptions.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequestById {

    private UUID venueId;

}
