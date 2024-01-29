package com.gleb.vinnikov.venue.users.api;

import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import com.gleb.vinnikov.venue.db.repos.VenueRepo;
import com.gleb.vinnikov.venue.users.services.UsersService;
import com.gleb.vinnikov.venue.utils.Utils;
import com.gleb.vinnikov.venue.venues.api.VenueCreationData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static com.gleb.vinnikov.venue.utils.Utils.initControllerTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT360S")
public class UsersControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private UsersService usersService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtService jwtService;
    private String getUserByUsernamePath;
    private String getMyInfoPath;

    @Before
    public void init() {
        initControllerTest(jwtService, userDetailsService);
        getUserByUsernamePath = Utils.buildPath(port, "get-user-by-username");
        getMyInfoPath = Utils.buildPath(port, "change-my-info");

    }

    @Test
    public void testGetUserByUsername() {
        when(usersService.getUserByUsername(TEST_USER_1.getUsername())).thenReturn(TEST_USER_RESPONSE_PUBLIC_1);
        getUserByUsername(TEST_USER_1.getUsername()).expectStatus().isOk().expectBody(UserResponsePublic.class)
                .isEqualTo(TEST_USER_RESPONSE_PUBLIC_1);
    }

    @Test
    public void testGetUserByUsernameNotFound() {
        when(usersService.getUserByUsername(TEST_USER_1.getUsername())).thenThrow(new NoSuchElementException());
        getUserByUsername(TEST_USER_1.getUsername()).expectStatus().isNotFound();
    }

    @Test
    public void testChangeMyInfo() {
        String oldUsername = TEST_USER_1.getUsername();
        String oldEmail = TEST_USER_1.getEmail();
        UserResponsePrivate expected = new UserResponsePrivate(TEST_USER_1.getId(),
                TEST_CHANGE_USER_INFO_REQUEST.getNewUsername(), TEST_CHANGE_USER_INFO_REQUEST.getNewEmail());
        when(usersService.updateUser(TEST_USER_1, TEST_CHANGE_USER_INFO_REQUEST)).thenReturn(expected);
        changeMyInfo(TEST_CHANGE_USER_INFO_REQUEST).expectStatus().isOk().expectBody(UserResponsePrivate.class)
                .isEqualTo(expected);
        TEST_USER_1.setUsername(oldUsername);
        TEST_USER_1.setEmail(oldEmail);

    }

    @Test
    public void testChangeMyInfoUsernameOnly() {
        String oldUsername = TEST_USER_1.getUsername();
        UserResponsePrivate expected = new UserResponsePrivate(TEST_USER_1.getId(),
                TEST_CHANGE_USER_INFO_REQUEST_USERNAME_ONLY.getNewUsername(), TEST_USER_1.getEmail());
        when(usersService.updateUser(TEST_USER_1, TEST_CHANGE_USER_INFO_REQUEST_USERNAME_ONLY)).thenReturn(expected);
        changeMyInfo(TEST_CHANGE_USER_INFO_REQUEST_USERNAME_ONLY).expectStatus().isOk()
                .expectBody(UserResponsePrivate.class).isEqualTo(expected);
        TEST_USER_1.setUsername(oldUsername);
    }

    @Test
    public void testChangeMyInfoEmailOnly() {
        String oldEmail = TEST_USER_1.getEmail();
        UserResponsePrivate expected = new UserResponsePrivate(TEST_USER_1.getId(), TEST_USER_1.getUsername(),
                TEST_CHANGE_USER_INFO_REQUEST_EMAIL_ONLY.getNewEmail());
        when(usersService.updateUser(TEST_USER_1, TEST_CHANGE_USER_INFO_REQUEST_EMAIL_ONLY)).thenReturn(expected);
        changeMyInfo(TEST_CHANGE_USER_INFO_REQUEST_EMAIL_ONLY).expectStatus().isOk()
                .expectBody(UserResponsePrivate.class).isEqualTo(expected);
        TEST_USER_1.setEmail(oldEmail);
    }

    @Test
    public void testChangeMyInfoWrongFormats() {
        changeMyInfo(new ChangeUserInfoRequest("___", null)).expectStatus().isBadRequest();
        changeMyInfo(new ChangeUserInfoRequest(null, "___")).expectStatus().isBadRequest();
    }

    private WebTestClient.ResponseSpec getUserByUsername(String name) {
        return Utils.performJsonHttpGetRequest(webTestClient, getUserByUsernamePath + "?username=" + name);
    }

    private WebTestClient.ResponseSpec changeMyInfo(ChangeUserInfoRequest changeUserInfoRequest) {
        return Utils.performJsonHttpPostRequest(webTestClient, getMyInfoPath, changeUserInfoRequest);
    }

}
