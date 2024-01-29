package com.gleb.vinnikov.venue.auth.jwt;

import com.gleb.vinnikov.venue.auth.jwt.impl.JwtServiceImpl;
import com.gleb.vinnikov.venue.db.entities.User;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtServiceTest {

    private JwtServiceImpl jwtService = new JwtServiceImpl(
            "4125442A472D4B6150645367566B58703273357638792F423F4528482B4D6251", 1000000);

    private final String USERNAME = "brenscrazy";

    @Test
    public void testIfTokenIsCorrect() {
        String token = jwtService.generateAccessToken(User.builder().username(USERNAME).build());
        Claims claims = jwtService.getClaims(token);
        String decodedUsername = claims.getSubject();
        long issuedAt = claims.getIssuedAt().getTime();
        long expiresAt = claims.getExpiration().getTime();
        Assertions.assertEquals(USERNAME, decodedUsername);
        Assertions.assertTrue(issuedAt < expiresAt);
    }

    @Test
    public void noUsernameTest() {
        assertThrows(IllegalArgumentException.class, () -> jwtService.generateAccessToken(User.builder().build()));
    }

}
