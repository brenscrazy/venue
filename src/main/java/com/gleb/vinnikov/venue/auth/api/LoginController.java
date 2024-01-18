package com.gleb.vinnikov.venue.auth.api;

import com.gleb.vinnikov.venue.auth.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authorization", description = "Методы для аутентификации и регистрации.")
public class LoginController {

    private final LoginService loginService;

    @Operation(
            summary = "Зарегистрировать нового пользователя.",
            description = "Принимает необходимую информацию и регистрирует нового пользователя в сервисе. Если " +
                    "переданная информация корректна, то возвращает JWT токен, который необходимо передать в " +
                    "заголовок Authorization для доступа к другим API методам."
    )
    @PostMapping(
            value = "/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> registration(
            @Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok().body(loginService.registration(request));
    }

    @Operation(
            summary = "Войти в сервис.",
            description = "Принимает имя пользователя и пароль зарегистрированного пользователя. Если " +
                    "переданная информация корректна, то возвращает JWT токен, который необходимо передать в " +
                    "заголовок Authorization для доступа к другим API методам."
    )
    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(loginService.login(request));
    }

}
