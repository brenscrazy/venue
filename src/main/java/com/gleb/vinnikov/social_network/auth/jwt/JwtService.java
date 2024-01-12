package com.gleb.vinnikov.social_network.auth.jwt;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateAccessToken(@NonNull UserDetails userDetails);

    Claims getClaims(@NonNull String token);

}
