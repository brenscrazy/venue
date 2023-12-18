package com.gleb.vinnikov.social_network.auth.api;

import com.gleb.vinnikov.social_network.auth.services.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class LoginController {

    private final LoginService loginService;

    @PostMapping(
            value = "/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> registration(
            @Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok().body(loginService.registration(request));
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(loginService.login(request));
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class})
    public ResponseEntity<String> handleBadCredentialsException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Bad credentials. Please check your username and password");// TODO: 08.06.2023 add message to properties
    }

}
