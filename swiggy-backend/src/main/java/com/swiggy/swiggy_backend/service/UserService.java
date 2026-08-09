package com.swiggy.swiggy_backend.service;


import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.UserRegistrationRequest;
import com.swiggy.swiggy_backend.dto.UserResponse;

@Service
public interface UserService {

    UserResponse register(UserRegistrationRequest request);

}
