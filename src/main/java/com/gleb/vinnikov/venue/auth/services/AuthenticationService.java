package com.gleb.vinnikov.venue.auth.services;

import com.gleb.vinnikov.venue.db.entities.User;
import org.springframework.stereotype.Service;

public interface AuthenticationService {

    public User getPrincipal();

}
