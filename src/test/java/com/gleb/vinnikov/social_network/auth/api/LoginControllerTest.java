package com.gleb.vinnikov.social_network.auth.api;

import com.gleb.vinnikov.social_network.auth.services.LoginService;
import com.gleb.vinnikov.social_network.db.entities.Role;
import com.gleb.vinnikov.social_network.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class LoginControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private LoginService loginService;

    private String loginPath;
    private String registerPath;
    private final String USERNAME = "brenscrazy";
    private final String EMAIL = "glebmanufa@gmail.com";
    private final String PASSWORD = "qwerty123";

    @Before
    public void init() {
        loginPath = "http://localhost:" + port + "/login";
        registerPath = "http://localhost:" + port + "/registration";
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(300))
                .build();
    }

    @Test
    public void noUserLoginTest() {
        when(loginService.login(any())).thenThrow(BadCredentialsException.class);
        login(new LoginRequest(USERNAME, PASSWORD)).expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void registrationTest() {
        when(loginService.registration(any())).thenReturn(new LoginResponse("CORRECT_TOKEN"));
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    public void registrationEmailIsTakenTest() {
        when(loginService.registration(any())).thenReturn(new LoginResponse("CORRECT_TOKEN"));
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        when(loginService.registration(any())).thenThrow(IllegalArgumentException.class);
        registration(new RegistrationRequest(USERNAME + "123", EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void registrationUsernameIsTakenTest() {
        when(loginService.registration(any())).thenReturn(new LoginResponse("CORRECT_TOKEN"));
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        when(loginService.registration(any())).thenThrow(IllegalArgumentException.class);
        registration(new RegistrationRequest(USERNAME, EMAIL + "any", PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void correctLoginTest() {
        when(loginService.registration(any())).thenReturn(new LoginResponse("CORRECT_TOKEN"));
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        when(loginService.login(any())).thenReturn(new LoginResponse("CORRECT_TOKEN"));
        login(new LoginRequest(USERNAME, PASSWORD)).expectStatus().isEqualTo(HttpStatus.OK);
    }

    @Test
    public void wrongPasswordTest() {
        when(loginService.registration(any())).thenReturn(new LoginResponse("CORRECT_TOKEN"));
        registration(new RegistrationRequest(USERNAME, EMAIL, PASSWORD, Role.USER))
                .expectStatus().isEqualTo(HttpStatus.OK);
        when(loginService.login(any())).thenThrow(BadCredentialsException.class);
        login(new LoginRequest(USERNAME, "wrong_password")).expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private WebTestClient.ResponseSpec registration(RegistrationRequest request) {
        return Utils.performJsonHttpPostRequestNoToken(webTestClient, registerPath, request);
    }

    private WebTestClient.ResponseSpec login(LoginRequest request) {
        return Utils.performJsonHttpPostRequestNoToken(webTestClient, loginPath, request);
    }

}
