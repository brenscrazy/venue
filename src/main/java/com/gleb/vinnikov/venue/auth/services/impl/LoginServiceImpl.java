package com.gleb.vinnikov.venue.auth.services.impl;

import com.gleb.vinnikov.venue.auth.api.LoginRequest;
import com.gleb.vinnikov.venue.auth.api.LoginResponse;
import com.gleb.vinnikov.venue.auth.api.RegistrationRequest;
import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import com.gleb.vinnikov.venue.auth.services.LoginService;
import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    //private final UserInfoValidator userInfoValidator;

    @Override
    public LoginResponse registration(RegistrationRequest registrationRequest) {
//        userInfoValidator.validateEmail(registrationRequest.getEmail());
//        userInfoValidator.validateUsername(registrationRequest.getUsername());
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .role(registrationRequest.getRole())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        User saved = userRepo.save(user);
        String accessToken = jwtService.generateAccessToken(saved);
        return new LoginResponse(accessToken);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        UserDetails user = (UserDetails) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword())).getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponse(accessToken);
    }

}
