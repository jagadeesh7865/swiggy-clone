package com.swiggy.swiggy_backend.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.swiggy_backend.dto.LoginRequest;
import com.swiggy.swiggy_backend.dto.LoginResponse;
import com.swiggy.swiggy_backend.repository.UserRepository;
import com.swiggy.swiggy_backend.security.CustomUserDetailsService;
import com.swiggy.swiggy_backend.security.JwtAuthenticationFilter;
import com.swiggy.swiggy_backend.security.JwtService;
import com.swiggy.swiggy_backend.service.AuthService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

	

	    @Autowired
	    private MockMvc mockMvc;
	    
	    @MockBean
	    private UserRepository userRepository;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @MockBean
	    private AuthService authService;

	    @MockBean
	    private JwtAuthenticationFilter jwtAuthenticationFilter;

	    @MockBean
	    private JwtService jwtService;

	    @MockBean
	    private CustomUserDetailsService customUserDetailsService;
	
    
    
	    @Test
	    void shouldLoginSuccessfully() throws Exception {

	        LoginRequest request = new LoginRequest();
	        request.setEmail("john@test.com");
	        request.setPassword("password123");

	        LoginResponse response = new LoginResponse();
	        response.setToken("jwt-token");

	        when(authService.login(any(LoginRequest.class)))
	                .thenReturn(response);

	        mockMvc.perform(post("/api/auth/login")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(request)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.token").value("jwt-token"));

	        verify(authService).login(any(LoginRequest.class));
	    }
    
    @Test
    void shouldReturnBadRequestWhenLoginRequestIsInvalid() throws Exception {

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "email":"",
                    "password":""
                }
                """))
                .andExpect(status().isBadRequest());

        verify(authService, never()).login(any());
    }
}