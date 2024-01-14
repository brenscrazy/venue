package com.gleb.vinnikov.venue.auth.services.impl;

import com.gleb.vinnikov.venue.auth.services.AuthenticationService;
import com.gleb.vinnikov.venue.db.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public User getPrincipal() {
        Object userObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(userObj instanceof User)) {
            throw new IllegalStateException("Saved principal is not User class.");
        }
        return (User) userObj;
    }

}
