package com.gleb.vinnikov.venue.db.repos;

import com.gleb.vinnikov.venue.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsernameOrEmail(String username, String email);

    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

}
