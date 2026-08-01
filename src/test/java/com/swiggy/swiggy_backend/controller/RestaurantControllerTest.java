package com.swiggy.swiggy_backend.controller;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.swiggy.swiggy_backend.dto.RestaurantPageResponse;
import com.swiggy.swiggy_backend.dto.RestaurantRequest;
import com.swiggy.swiggy_backend.dto.RestaurantResponse;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.security.CustomUserDetailsService;
import com.swiggy.swiggy_backend.security.JwtAuthenticationFilter;
import com.swiggy.swiggy_backend.security.JwtService;
import com.swiggy.swiggy_backend.service.RestaurantService;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private RestaurantService restaurantService;
    
    @Test
    void shouldReturnRestaurantById() throws Exception {

        RestaurantResponse response = new RestaurantResponse();

        response.setId(1L);
        response.setName("Dominos");
        response.setDescription("Pizza");

        when(restaurantService.getRestaurantById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/restaurants/1"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(1))

                .andExpect(jsonPath("$.name").value("Dominos"))

                .andExpect(jsonPath("$.description").value("Pizza"));
    }
    
    @Test
    void shouldRegisterRestaurant() throws Exception {

        RestaurantRequest request = new RestaurantRequest();
        request.setName("Dominos");
        request.setDescription("Pizza");

        RestaurantResponse response = new RestaurantResponse();
        response.setId(1L);
        response.setName("Dominos");
        response.setDescription("Pizza");

        when(restaurantService.registerRestaurant(any(RestaurantRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "name":"Dominos",
                  "description":"Pizza",
                  "address":"MG Road",
                  "city":"Hyderabad",
                  "state":"Telangana",
                  "pincode":"500001",
                  "phone":"9876543210",
                  "email":"dominos@test.com"
                }
                """))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dominos"))
                .andExpect(jsonPath("$.description").value("Pizza"));
        
        

        verify(restaurantService).registerRestaurant(any(RestaurantRequest.class));
    }
    
    @Test
    void shouldUpdateRestaurant() throws Exception {

        RestaurantResponse response = new RestaurantResponse();
        response.setId(1L);
        response.setName("Updated Restaurant");
        response.setDescription("Updated Description");

        when(restaurantService.updateRestaurant(eq(1L), any(RestaurantRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/restaurants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "name":"Updated Restaurant",
                  "description":"Updated Description",
                  "address":"MG Road",
                  "city":"Hyderabad",
                  "state":"Telangana",
                  "pincode":"500001",
                  "phone":"9876543210",
                  "email":"updated@test.com"
                }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Restaurant"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        verify(restaurantService)
                .updateRestaurant(eq(1L), any(RestaurantRequest.class));
    }
    
    
    @Test
    void shouldReturnAllRestaurants() throws Exception {

        RestaurantResponse restaurant = new RestaurantResponse();

        restaurant.setId(1L);
        restaurant.setName("Dominos");
        restaurant.setDescription("Pizza");

        RestaurantPageResponse pageResponse = new RestaurantPageResponse();

        pageResponse.setContent(List.of(restaurant));
        pageResponse.setPage(0);
        pageResponse.setSize(5);
        pageResponse.setTotalElements(1);
        pageResponse.setTotalPages(1);
        pageResponse.setLast(true);

        when(restaurantService.getAllRestaurants(0, 5, "name", "asc"))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/restaurants")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "name")
                .param("direction", "asc"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Dominos"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.last").value(true));

        verify(restaurantService)
                .getAllRestaurants(0, 5, "name", "asc");
    }
    
    @Test
    void shouldReturn404WhenRestaurantNotFound() throws Exception {

        when(restaurantService.getRestaurantById(1L))
                .thenThrow(new ResourceNotFoundException("Restaurant not found"));

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isNotFound());

        verify(restaurantService).getRestaurantById(1L);
    }
	
    @Test
    void shouldReturnBadRequestWhenRestaurantRequestIsInvalid() throws Exception {

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "",
                    "description": "Pizza"
                }
                """))
                .andExpect(status().isBadRequest());

        verify(restaurantService, never())
                .registerRestaurant(any(RestaurantRequest.class));
    }
}