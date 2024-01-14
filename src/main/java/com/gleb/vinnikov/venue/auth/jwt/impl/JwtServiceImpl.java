package com.gleb.vinnikov.venue.auth.jwt.impl;

import com.gleb.vinnikov.venue.auth.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final Key key;
    private final long expirationMillis;

    public JwtServiceImpl(
            @Value("${application.security.jwt.secret-key}") String secret,
            @Value("${application.security.jwt.expiration-millis}") long expirationMillis) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMillis = expirationMillis;
    }

    @Override
    public String generateAccessToken(@NonNull UserDetails userDetails) {
        String username = userDetails.getUsername();
        if (username == null) {
            throw new IllegalArgumentException("Given user details have null username.");
        }
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Claims getClaims(@NonNull String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
