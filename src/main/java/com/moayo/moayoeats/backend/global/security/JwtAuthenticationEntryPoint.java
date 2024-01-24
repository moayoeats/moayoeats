package com.moayo.moayoeats.backend.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest req,
        HttpServletResponse res,
        AuthenticationException authException
    ) throws IOException, ServletException, IOException {

        res.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
    }
}
