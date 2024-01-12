package com.gleb.vinnikov.social_network.auth.services;

import com.gleb.vinnikov.social_network.auth.api.LoginRequest;
import com.gleb.vinnikov.social_network.auth.api.LoginResponse;
import com.gleb.vinnikov.social_network.auth.api.RegistrationRequest;

public interface LoginService {

    LoginResponse registration(RegistrationRequest registrationRequest);

    LoginResponse login(LoginRequest request);

}
