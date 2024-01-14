package com.gleb.vinnikov.venue.auth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpHeaderNames.AUTHORIZATION;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{" +
                "\"errorMessage\": \"Unable to authorize. Please check if you provided correct JWT token in an " +
                AUTHORIZATION + " header. You can get your JWT token by logging in with /login endpoint or by " +
                "registering with /registration endpoint.\"" +
                "}");

    }
}
