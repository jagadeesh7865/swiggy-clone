package com.swiggy.swiggy_backend.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.swiggy.swiggy_backend.TestMailConfig;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestMailConfig.class)
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {

        String request = """
        {
            "firstName":"John",
            "lastName":"Doe",
            "email":"john@test.com",
            "mobile":"9876543210",
            "password":"password123"
        }
        """;

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@test.com"));
    }
}