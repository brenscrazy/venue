package com.gleb.vinnikov.social_network.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user_venue_subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVenueSubscription<UserT, VenueT> {

    @EmbeddedId
    private UserVenueIds ids;

    public UserVenueSubscription(UserT user, VenueT venue) {
        ids = new UserVenueIds(user, venue);
    }

    public UserVenueSubscription(UserT user) {
        ids = UserVenueIds.builder().user(user).build();
    }

    public VenueT getVenue() {
        return ids.getVenue();
    };

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Embeddable
    public class UserVenueIds implements Serializable {

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserT user;
        @ManyToOne
        @JoinColumn(name = "venue_id")
        private VenueT venue;

    }

}
