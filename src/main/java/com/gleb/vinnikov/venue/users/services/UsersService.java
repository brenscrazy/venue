package com.gleb.vinnikov.venue.users.services;

import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.users.api.ChangeUserInfoRequest;
import com.gleb.vinnikov.venue.users.api.UserResponsePrivate;
import com.gleb.vinnikov.venue.users.api.UserResponsePublic;
import lombok.NonNull;

public interface UsersService {

    UserResponsePublic getUserByUsername(@NonNull String username);

    UserResponsePrivate updateUser(@NonNull User principal, @NonNull ChangeUserInfoRequest request);

}
