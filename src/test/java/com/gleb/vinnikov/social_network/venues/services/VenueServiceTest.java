package com.gleb.vinnikov.social_network.venues.services;

import com.gleb.vinnikov.social_network.db.repos.VenueRepo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.testcontainers.shaded.org.awaitility.Awaitility.given;

@ExtendWith(MockitoExtension.class)
public class VenueServiceTest {

    @Mock
    private VenueRepo venueRepo;

    @InjectMocks
    private VenueService venueService;



}
