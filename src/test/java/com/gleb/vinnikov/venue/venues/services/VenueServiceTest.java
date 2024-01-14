package com.gleb.vinnikov.venue.venues.services;

import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VenueServiceTest {

    @Mock
    private VenueRepo venueRepo;

    @InjectMocks
    private VenueService venueService;



}
