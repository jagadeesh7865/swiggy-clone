package com.swiggy.swiggy_backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.UserRegistrationRequest;
import com.swiggy.swiggy_backend.dto.UserResponse;
import com.swiggy.swiggy_backend.entity.Role;
import com.swiggy.swiggy_backend.entity.User;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.UserRepository;
import com.swiggy.swiggy_backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository,
                           PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse register(UserRegistrationRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new ResourceNotFoundException("Email already exists");
        }

        if (repository.existsByMobile(request.getMobile())) {
            throw new ResourceNotFoundException("Mobile already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());

        // We will encrypt password in Step 3
        user.setPassword(

                passwordEncoder.encode(

                        request.getPassword()

                )

        );

        user.setRole(Role.CUSTOMER);

        User savedUser = repository.save(user);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setMobile(savedUser.getMobile());
        response.setRole(savedUser.getRole().name());

        return response;

    }

}