package com.gleb.vinnikov.social_network.db.repos;

import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.entities.UserVenueSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

public interface UserVenueSubscriptionsRepo extends JpaRepository<UserVenueSubscription, UUID> {

    @Query("select count(s.id) <> 0 from UserVenueSubscription s where s.user.id = ?1 and s.venue.id = ?2")
    boolean existsByUserIdAndVenueId(UUID userId, UUID venueId);

    @Query("select s from UserVenueSubscription s where s.venue.id = ?1")
    List<UserVenueSubscription.SubscriptionUserOnly> findUsersByVenueId(UUID venueId);

    @Query("select s from UserVenueSubscription s where s.venue.idName = ?1")
    List<UserVenueSubscription.SubscriptionUserOnly> findUsersByVenueIdName(String venueId);

}
