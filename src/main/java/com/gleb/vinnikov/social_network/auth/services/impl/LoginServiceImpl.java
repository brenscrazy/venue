package com.gleb.vinnikov.social_network.auth.services.impl;

import com.gleb.vinnikov.social_network.auth.api.LoginRequest;
import com.gleb.vinnikov.social_network.auth.api.LoginResponse;
import com.gleb.vinnikov.social_network.auth.api.RegistrationRequest;
import com.gleb.vinnikov.social_network.auth.jwt.JwtService;
import com.gleb.vinnikov.social_network.auth.services.LoginService;
import com.gleb.vinnikov.social_network.db.entities.User;
import com.gleb.vinnikov.social_network.db.repos.UserRepo;
import com.gleb.vinnikov.social_network.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Pattern emailPattern;
    private final Pattern usernamePattern;

    @Override
    public LoginResponse registration(RegistrationRequest registrationRequest) {
        validateEmail(registrationRequest.getEmail());
        validateUsername(registrationRequest.getUsername());
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

    private void validateEmail(String email) {
        if (!emailPattern.matcher(email).matches()) {
            throw ExceptionUtils.buildIllegalArgumentException("registration.error.wrong.email.format", email);
        }
        if (userRepo.existsUserByEmail(email)) {
            throw ExceptionUtils.buildIllegalArgumentException("registration.error.email.is.taken", email);
        }
    }

    private void validateUsername(String username) {
        if (!usernamePattern.matcher(username).matches()) {
            throw ExceptionUtils.buildIllegalArgumentException("registration.error.wrong.username.format", username);
        }
        if (userRepo.existsUserByUsername(username)) {
            throw ExceptionUtils.buildIllegalArgumentException("registration.error.username.is.taken", username);
        }
    }

}
