package com.gleb.vinnikov.venue.venues.services;

import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import com.gleb.vinnikov.venue.utils.Constants;
import com.gleb.vinnikov.venue.venues.services.impl.VenueServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VenueServiceTest {

    @Mock
    private VenueRepo venueRepo;
    @InjectMocks
    private VenueServiceImpl venueService;

    @Test
    public void testGetById() {
        when(venueRepo.findById(TEST_VENUE_1.getId())).thenReturn(Optional.of(TEST_VENUE_1));
        Assertions.assertEquals(TEST_VENUE_RESPONSE_1, venueService.getById(TEST_VENUE_1.getId()));
    }

    @Test
    public void testGetByIdNotFound() {
        when(venueRepo.findById(TEST_VENUE_1.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> venueService.getById(TEST_VENUE_1.getId()));
    }

    @Test
    public void testGetByIdName() {
        when(venueRepo.findByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.of(TEST_VENUE_1));
        Assertions.assertEquals(TEST_VENUE_RESPONSE_1, venueService.getByIdName(TEST_VENUE_1.getIdName()));
    }

    @Test
    public void testGetByIdNameNotFound() {
        when(venueRepo.findByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> venueService.getByIdName(TEST_VENUE_1.getIdName()));
    }

    @Test
    public void testGetByDisplayName() {
        when(venueRepo.findAllByDisplayNameOrderByIdName(TEST_VENUE_1.getDisplayName())).thenReturn(List.of(TEST_VENUE_1));
        Assertions.assertEquals(List.of(TEST_VENUE_RESPONSE_1), venueService.getByDisplayName(TEST_VENUE_1.getDisplayName()));
    }

    @Test
    public void testGetByNamePrefix() {
        when(venueRepo.findAllByDisplayNameStartingWithOrIdNameStartingWithOrderByDisplayNameAscIdNameAsc(
                "ve", "ve")).thenReturn(List.of(TEST_VENUE_1));
        Assertions.assertEquals(List.of(TEST_VENUE_RESPONSE_1), venueService.getByNamePrefix("ve"));
    }

    @Test
    public void testAddVenue() {
        when(venueRepo.save(any())).thenReturn(TEST_VENUE_1);
        Assertions.assertEquals(TEST_VENUE_RESPONSE_1, venueService.addVenue(TEST_VENUE_CREATION_DATA,
                Constants.TEST_USER_1));
    }

    @Test
    public void testAddVenueIdNameTaken() {
        when(venueRepo.save(any())).thenThrow(DataIntegrityViolationException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> venueService.addVenue(TEST_VENUE_CREATION_DATA,
                Constants.TEST_USER_1));
    }



}
