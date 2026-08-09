package com.swiggy.swiggy_backend.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.LoginRequest;
import com.swiggy.swiggy_backend.dto.LoginResponse;
import com.swiggy.swiggy_backend.security.JwtService;
import com.swiggy.swiggy_backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String role =
                userDetails.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace("ROLE_", "");

        Map<String, Object> claims =
                new HashMap<>();

        claims.put("role", role);

        String token =
                jwtService.generateToken(
                        claims,
                        request.getEmail()
                );

        return new LoginResponse(
                token,
                "Login Successful"
        );
    }
}