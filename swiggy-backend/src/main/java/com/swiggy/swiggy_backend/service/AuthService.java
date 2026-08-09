package com.swiggy.swiggy_backend.service;

import com.swiggy.swiggy_backend.dto.LoginRequest;
import com.swiggy.swiggy_backend.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}