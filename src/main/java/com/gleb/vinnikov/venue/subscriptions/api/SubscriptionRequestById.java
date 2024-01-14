package com.gleb.vinnikov.venue.subscriptions.api;

import lombok.Data;

import java.util.UUID;

@Data
public class SubscriptionRequestById {

    private UUID venueId;

}
