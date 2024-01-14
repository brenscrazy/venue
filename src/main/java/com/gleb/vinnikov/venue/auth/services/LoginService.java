package com.gleb.vinnikov.venue.auth.services;

import com.gleb.vinnikov.venue.auth.api.LoginRequest;
import com.gleb.vinnikov.venue.auth.api.LoginResponse;
import com.gleb.vinnikov.venue.auth.api.RegistrationRequest;

public interface LoginService {

    LoginResponse registration(RegistrationRequest registrationRequest);

    LoginResponse login(LoginRequest request);

}
