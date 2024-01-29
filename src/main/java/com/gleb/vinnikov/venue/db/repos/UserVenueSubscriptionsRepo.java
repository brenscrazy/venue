package com.gleb.vinnikov.venue.db.repos;

import com.gleb.vinnikov.venue.db.entities.UserVenueSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserVenueSubscriptionsRepo extends JpaRepository<UserVenueSubscription, UUID> {

    @Query("select s.id from UserVenueSubscription s where s.user.id = ?1 and s.venue.id = ?2")
    Optional<UUID> findIdByUserIdAndVenueId(UUID userId, UUID venueId);

    @Query("select count(s.id) <> 0 from UserVenueSubscription s where s.user.id = ?1 and s.venue.id = ?2")
    boolean existsByUserIdAndVenueId(UUID userId, UUID venueId);

    @Query("select s from UserVenueSubscription s where s.venue.id = ?1")
    List<UserVenueSubscription> findUsersByVenueId(UUID venueId);

    @Query("select s from UserVenueSubscription s where s.venue.idName = ?1")
    List<UserVenueSubscription> findUsersByVenueIdName(String venueId);

}
