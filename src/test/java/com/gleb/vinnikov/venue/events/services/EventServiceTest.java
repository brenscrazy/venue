package com.gleb.vinnikov.venue.events.services;

import com.gleb.vinnikov.venue.db.entities.Event;
import com.gleb.vinnikov.venue.db.repos.EventRepo;
import com.gleb.vinnikov.venue.db.repos.UserRepo;
import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import com.gleb.vinnikov.venue.events.api.GetEventRequest;
import com.gleb.vinnikov.venue.users.services.impl.UsersServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @Mock
    private EventRepo eventRepo;
    @Mock
    private VenueRepo venueRepo;
    @InjectMocks
    private EventService eventService;

    @Test
    public void testCreateEvent() {
        when(eventRepo.save(any())).thenReturn(TEST_EVENT_1);
        when(venueRepo.findByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.of(TEST_VENUE_1));
        assertEquals(TEST_EVENT_RESPONSE_1, eventService.createEvent(TEST_USER_1, TEST_EVENT_REQUEST_1));
    }

    @Test
    public void testCreateEventNoVenue() {
        when(venueRepo.findByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> eventService.createEvent(TEST_USER_1, TEST_EVENT_REQUEST_1));
    }

    @Test
    public void testGetEvent() {
        when(eventRepo.findById(TEST_EVENT_1.getId())).thenReturn(Optional.of(TEST_EVENT_1));
        assertEquals(TEST_EVENT_RESPONSE_1, eventService.getEvent(TEST_EVENT_1.getId()));
    }

    @Test
    public void testGetEventNotFound() {
        when(eventRepo.findById(TEST_EVENT_1.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> eventService.getEvent(TEST_EVENT_1.getId()));
    }

    @Test
    public void testGetEvents() {
        when(venueRepo.findByIdName(TEST_VENUE_1.getIdName())).thenReturn(Optional.of(TEST_VENUE_1));
        GetEventRequest getEventRequest = new GetEventRequest(TEST_VENUE_1.getIdName());
        when(eventRepo.findByTakesPlaceAtAndBeforeAndAfter(TEST_VENUE_1, getEventRequest.getAfter().toTimestamp(),
                getEventRequest.getBefore().toTimestamp())).thenReturn(List.of(TEST_EVENT_1, TEST_EVENT_2));
        assertEquals(List.of(TEST_EVENT_RESPONSE_1, TEST_EVENT_RESPONSE_2), eventService.getEvents(getEventRequest));
    }

    @Test
    public void testGetEventsNoVenue() {
        GetEventRequest getEventRequest = new GetEventRequest();
        when(eventRepo.findByBeforeAndAfter(getEventRequest.getAfter().toTimestamp(),
                getEventRequest.getBefore().toTimestamp())).thenReturn(List.of(TEST_EVENT_1, TEST_EVENT_2));
        assertEquals(List.of(TEST_EVENT_RESPONSE_1, TEST_EVENT_RESPONSE_2), eventService.getEvents(getEventRequest));
    }

}
