package com.gleb.vinnikov.venue.users.services;


import com.gleb.vinnikov.venue.db.entities.User;
import com.gleb.vinnikov.venue.db.repos.UserRepo;
import com.gleb.vinnikov.venue.users.api.UserResponsePrivate;
import com.gleb.vinnikov.venue.users.services.impl.UsersServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;

import java.util.Optional;

import static com.gleb.vinnikov.venue.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;
    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    public void testGetUserByUsername() {
        when(userRepo.findFirstByUsername(TEST_USER_1.getUsername())).thenReturn(Optional.of(TEST_USER_1));
        assertEquals(usersService.getUserByUsername(TEST_USER_1.getUsername()), TEST_USER_RESPONSE_PUBLIC_1);
    }

    @Test
    public void testChangeMyInfo() {
        String oldUsername = TEST_USER_1.getUsername();
        String oldEmail = TEST_USER_1.getEmail();
        when(userRepo.findOne(Example.of(TEST_USER_1))).thenReturn(Optional.of(TEST_USER_1));
        User updated = new User(TEST_USER_1.getId(), TEST_CHANGE_USER_INFO_REQUEST.getNewUsername(),
                TEST_USER_1.getPassword(), TEST_CHANGE_USER_INFO_REQUEST.getNewEmail(), TEST_USER_1.getRole());
        when(userRepo.save(updated)).thenReturn(updated);
        assertEquals(usersService.updateUser(TEST_USER_1, TEST_CHANGE_USER_INFO_REQUEST),
                new UserResponsePrivate(updated.getId(), updated.getUsername(), updated.getEmail()));
        TEST_USER_1.setUsername(oldUsername);
        TEST_USER_1.setEmail(oldEmail);
    }

    @Test
    public void testChangeMyInfoUsernameOnly() {
        String oldUsername = TEST_USER_1.getUsername();
        when(userRepo.findOne(Example.of(TEST_USER_1))).thenReturn(Optional.of(TEST_USER_1));
        User updated = new User(TEST_USER_1.getId(), TEST_CHANGE_USER_INFO_REQUEST_USERNAME_ONLY.getNewUsername(),
                TEST_USER_1.getPassword(), TEST_USER_1.getEmail(), TEST_USER_1.getRole());
        when(userRepo.save(updated)).thenReturn(updated);
        assertEquals(usersService.updateUser(TEST_USER_1, TEST_CHANGE_USER_INFO_REQUEST_USERNAME_ONLY),
                new UserResponsePrivate(updated.getId(), updated.getUsername(), updated.getEmail()));
        TEST_USER_1.setUsername(oldUsername);
    }

    @Test
    public void testChangeMyInfoEmailOnly() {
        String oldEmail = TEST_USER_1.getEmail();
        when(userRepo.findOne(Example.of(TEST_USER_1))).thenReturn(Optional.of(TEST_USER_1));
        User updated = new User(TEST_USER_1.getId(), TEST_USER_1.getUsername(),
                TEST_USER_1.getPassword(), TEST_CHANGE_USER_INFO_REQUEST_EMAIL_ONLY.getNewEmail(), TEST_USER_1.getRole());
        when(userRepo.save(updated)).thenReturn(updated);
        assertEquals(usersService.updateUser(TEST_USER_1, TEST_CHANGE_USER_INFO_REQUEST_EMAIL_ONLY),
                new UserResponsePrivate(updated.getId(), updated.getUsername(), updated.getEmail()));
        TEST_USER_1.setEmail(oldEmail);
    }

}
