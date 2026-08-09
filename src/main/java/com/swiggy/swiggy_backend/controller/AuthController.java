package com.swiggy.swiggy_backend.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.LoginRequest;
import com.swiggy.swiggy_backend.dto.LoginResponse;
import com.swiggy.swiggy_backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Validated
            @RequestBody LoginRequest request) {

        return authService.login(request);

    }
    
   

}