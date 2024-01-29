package com.gleb.vinnikov.venue.auth.services;

import com.gleb.vinnikov.venue.auth.api.LoginRequest;
import com.gleb.vinnikov.venue.auth.api.LoginResponse;
import com.gleb.vinnikov.venue.auth.api.RegistrationRequest;
import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import com.gleb.vinnikov.venue.auth.services.impl.LoginServiceImpl;
import com.gleb.vinnikov.venue.db.entities.Role;
import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.repos.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class LoginServiceTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private LoginServiceImpl loginService;
    private final String USERNAME = "brenscrazy";
    private final String EMAIL = "glebmanufa@gmail.com";
    private final String PASSWORD = "qwerty123";
    private final String TOKEN = "CORRECT_TOKEN";
    public static final String ENCODED_PASSWORD = "ENCODED PASSWORD";

    @Test
    public void correctRegistrationTest() {
        RegistrationRequest registrationRequest = new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER);
        User user = new User(UUID.randomUUID(), USERNAME, PASSWORD, EMAIL, Role.USER);
        when(passwordEncoder.encode(any())).thenReturn(ENCODED_PASSWORD);
        when(userRepo.save(any())).thenReturn(user);
        when(jwtService.generateAccessToken(user)).thenReturn(TOKEN);
        LoginResponse loginResponse = loginService.registration(registrationRequest);
        Assertions.assertThat(loginResponse.getAccessToken()).isEqualTo(TOKEN);
    }

    @Test
    public void correctLoginTest() {
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);
        User user = new User(UUID.randomUUID(), USERNAME, PASSWORD, EMAIL, Role.USER);
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        when(jwtService.generateAccessToken(user)).thenReturn(TOKEN);
        LoginResponse loginResponse = loginService.login(loginRequest);
        Assertions.assertThat(loginResponse.getAccessToken()).isEqualTo(TOKEN);
    }

    @Test
    public void badCredentialTest() {
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        Assert.assertThrows(BadCredentialsException.class, () -> loginService.login(loginRequest));
    }

}