package com.gleb.vinnikov.venue.users.services.impl;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.repos.UserRepo;
import com.gleb.vinnikov.venue.users.api.ChangeUserInfoRequest;
import com.gleb.vinnikov.venue.users.api.UserResponsePrivate;
import com.gleb.vinnikov.venue.users.api.UserResponsePublic;
import com.gleb.vinnikov.venue.users.services.UsersService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepo userRepo;

    @Override
    public UserResponsePublic getUserByUsername(@NonNull String username) {
        Optional<User> userOptional = userRepo.findFirstByUsername(username);
        User user = userOptional.orElseThrow(() -> new NotFoundException("No user with given username"));
        return new UserResponsePublic(user.getId(), user.getUsername());
    }

    @Override
    public UserResponsePrivate updateUser(@NonNull User principal, @NonNull ChangeUserInfoRequest request) {
        User user = userRepo.findOne(Example.of(principal)).orElseThrow(
                () -> new NoSuchElementException("Authorized user does not exists"));
        Optional.ofNullable(request.getNewEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(request.getNewUsername()).ifPresent(user::setUsername);
        User userUpdated = userRepo.save(user);
        return new UserResponsePrivate(userUpdated.getId(), userUpdated.getUsername(), userUpdated.getEmail());
    }
}
