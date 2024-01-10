package com.gleb.vinnikov.social_network.db.repos;

import com.gleb.vinnikov.social_network.db.entities.UserVenueSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserVenueSubscriptionsRepo extends JpaRepository<UserVenueSubscription, UserVenueSubscription.UserVenueIds> {



}
