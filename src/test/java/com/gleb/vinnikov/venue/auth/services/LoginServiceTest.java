package com.gleb.vinnikov.venue.auth.services;

import com.gleb.vinnikov.venue.auth.api.LoginRequest;
import com.gleb.vinnikov.venue.auth.api.LoginResponse;
import com.gleb.vinnikov.venue.auth.api.RegistrationRequest;
import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import com.gleb.vinnikov.venue.auth.services.impl.UserDetailsServiceImpl;
import com.gleb.vinnikov.venue.db.entities.Role;
import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.repos.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @MockBean
    private UserRepo userRepo;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginService loginService;
    private final String USERNAME = "brenscrazy";
    private final String EMAIL = "glebmanufa@gmail.com";
    private final String PASSWORD = "qwerty123";
    private final String TOKEN = "CORRECT_TOKEN";
    private final List<String> WRONG_EMAILS = List.of(
            "wrong.email.com", "wrong@emai@l.com", "wrong@ema_il.com", "", "a", "@"
    );
    private final List<String> WRONG_USERNAMES = List.of(
            "fen", "1fen", "_fen", "two fords", "two-words", "EnormouslyLongUsernameWithTooMuchSymbolsInIt"
    );

    @Test
    public void wrongEmailFormatRegistrationTest() {
        WRONG_EMAILS.forEach(email -> Assert.assertThrows(IllegalArgumentException.class, () ->
                loginService.registration(new RegistrationRequest(USERNAME, email, PASSWORD, Role.USER))));
    }

    @Test
    public void wrongUsernameFormatRegistrationTest() {
        WRONG_USERNAMES.forEach(email -> Assert.assertThrows(IllegalArgumentException.class, () ->
                loginService.registration(new RegistrationRequest(USERNAME, email, PASSWORD, Role.USER))));
    }

    @Test
    public void correctRegistrationTest() {
        RegistrationRequest registrationRequest = new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER);
        User user = new User(UUID.randomUUID(), USERNAME, PASSWORD, EMAIL, Role.USER);
        when(userRepo.save(any())).thenReturn(user);
        when(jwtService.generateAccessToken(user)).thenReturn(TOKEN);
        LoginResponse loginResponse = loginService.registration(registrationRequest);
        Assertions.assertThat(loginResponse.getAccessToken()).isEqualTo(TOKEN);
    }

    @Test
    public void correctLoginTest() {
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);
        User user = new User(UUID.randomUUID(), USERNAME, PASSWORD, EMAIL, Role.USER);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(user);
        when(jwtService.generateAccessToken(user)).thenReturn(TOKEN);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        LoginResponse loginResponse = loginService.login(loginRequest);
        Assertions.assertThat(loginResponse.getAccessToken()).isEqualTo(TOKEN);
    }

    @Test
    public void noUserFoundLoginTest() {
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenThrow(UsernameNotFoundException.class);
        Assert.assertThrows(BadCredentialsException.class, () -> loginService.login(loginRequest));
    }

    @Test
    public void wrongPasswordTest() {
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);
        User user = new User(UUID.randomUUID(), USERNAME, PASSWORD, EMAIL, Role.USER);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(user);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        Assert.assertThrows(BadCredentialsException.class, () -> loginService.login(loginRequest));
    }

}