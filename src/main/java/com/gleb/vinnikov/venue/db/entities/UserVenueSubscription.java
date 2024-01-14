package com.gleb.vinnikov.venue.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_venue_subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVenueSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    public UserVenueSubscription(User user, Venue venue) {
        this.setUser(user);
        this.setVenue(venue);
    }

    public UserVenueSubscription(UUID userId, UUID venueId) {
        this(User.builder().id(userId).build(), Venue.builder().id(venueId).build());
    }

    public UserVenueSubscription(User user) {
        this.setUser(user);
    }

    public interface SubscriptionVenueOnly{

       public Venue getVenue();

    }

    public interface SubscriptionUserOnly{

        public User.UserIdUsernameOnly getUser();

    }

}
