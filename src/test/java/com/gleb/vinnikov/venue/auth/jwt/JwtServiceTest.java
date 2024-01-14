package com.gleb.vinnikov.venue.auth.jwt;

import com.gleb.vinnikov.venue.db.entities.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    private final String USERNAME = "brenscrazy";

    @Test
    public void testIfTokenIsCorrect() {
        String token = jwtService.generateAccessToken(User.builder().username(USERNAME).build());
        Claims claims = jwtService.getClaims(token);
        String decodedUsername = claims.getSubject();
        long issuedAt = claims.getIssuedAt().getTime();
        Assertions.assertEquals(USERNAME, decodedUsername);
        Assertions.assertTrue(System.currentTimeMillis() - issuedAt < 1000);
    }

    @Test
    public void noUsernameTest() {
        assertThrows(IllegalArgumentException.class, () -> jwtService.generateAccessToken(User.builder().build()));
    }

}
