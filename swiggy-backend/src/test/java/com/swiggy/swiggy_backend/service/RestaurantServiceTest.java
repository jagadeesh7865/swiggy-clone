package com.swiggy.swiggy_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.swiggy.swiggy_backend.dto.RestaurantPageResponse;
import com.swiggy.swiggy_backend.dto.RestaurantRequest;
import com.swiggy.swiggy_backend.dto.RestaurantResponse;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.service.impl.RestaurantServiceImpl;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;
    
    
    @Test
    void shouldReturnRestaurantWhenIdExists() {

        Restaurant restaurant = new Restaurant();

        restaurant.setId(1L);
        restaurant.setName("Dominos");
        restaurant.setDescription("Pizza");
        restaurant.setAddress("MG Road");
        restaurant.setCity("Hyderabad");
        restaurant.setState("Telangana");
        restaurant.setPincode("500001");
        restaurant.setPhone("9999999999");
        restaurant.setEmail("dominos@test.com");
        restaurant.setImageUrl("image.png");
        restaurant.setActive(true);

        when(repository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        RestaurantResponse response =
                restaurantService.getRestaurantById(1L);

        assertNotNull(response);

        assertEquals(1L, response.getId());
        assertEquals("Dominos", response.getName());
        assertEquals("Pizza", response.getDescription());

        verify(repository).findById(1L);
    }
    
    
    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.getRestaurantById(1L)
        );

        verify(repository).findById(1L);
    }
    
    @Test
    void shouldRegisterRestaurant() {

        RestaurantRequest request = new RestaurantRequest();

        request.setName("KFC");
        request.setDescription("Chicken");
        request.setAddress("MG Road");
        request.setCity("Hyderabad");
        request.setState("Telangana");
        request.setPincode("500001");
        request.setPhone("9999999999");
        request.setEmail("kfc@test.com");
        request.setImageUrl("kfc.png");

        Restaurant savedRestaurant = new Restaurant();

        savedRestaurant.setId(1L);
        savedRestaurant.setName(request.getName());
        savedRestaurant.setDescription(request.getDescription());
        savedRestaurant.setAddress(request.getAddress());
        savedRestaurant.setCity(request.getCity());
        savedRestaurant.setState(request.getState());
        savedRestaurant.setPincode(request.getPincode());
        savedRestaurant.setPhone(request.getPhone());
        savedRestaurant.setEmail(request.getEmail());
        savedRestaurant.setImageUrl(request.getImageUrl());

        when(repository.save(any(Restaurant.class)))
                .thenReturn(savedRestaurant);

        RestaurantResponse response =
                restaurantService.registerRestaurant(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("KFC", response.getName());

        verify(repository).save(any(Restaurant.class));
    }
    
    @Test
    void shouldUpdateRestaurant() {

        Long id = 1L;

        Restaurant existingRestaurant = new Restaurant();
        existingRestaurant.setId(id);
        existingRestaurant.setName("Old Restaurant");
        existingRestaurant.setDescription("Old Description");
        existingRestaurant.setAddress("Old Address");
        existingRestaurant.setCity("Old City");
        existingRestaurant.setState("Old State");
        existingRestaurant.setPincode("111111");
        existingRestaurant.setPhone("9999999999");
        existingRestaurant.setEmail("old@test.com");
        existingRestaurant.setImageUrl("old.png");

        RestaurantRequest request = new RestaurantRequest();
        request.setName("New Restaurant");
        request.setDescription("New Description");
        request.setAddress("New Address");
        request.setCity("New City");
        request.setState("New State");
        request.setPincode("500001");
        request.setPhone("8888888888");
        request.setEmail("new@test.com");
        request.setImageUrl("new.png");

        when(repository.findById(id))
                .thenReturn(Optional.of(existingRestaurant));

        when(repository.save(any(Restaurant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RestaurantResponse response =
                restaurantService.updateRestaurant(id, request);

        assertNotNull(response);

        assertEquals("New Restaurant", response.getName());
        assertEquals("New Description", response.getDescription());
        assertEquals("New City", response.getCity());
        assertEquals("New State", response.getState());
        assertEquals("500001", response.getPincode());

        verify(repository).findById(id);
        verify(repository).save(any(Restaurant.class));
    }
    
    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingRestaurant() {

        Long id = 1L;

        RestaurantRequest request = new RestaurantRequest();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> restaurantService.updateRestaurant(id, request)
        );

        verify(repository).findById(id);
    }
    
    @Test
    void shouldReturnAllRestaurants() {

        Restaurant restaurant = new Restaurant();

        restaurant.setId(1L);
        restaurant.setName("Dominos");
        restaurant.setDescription("Pizza");
        restaurant.setAddress("MG Road");
        restaurant.setCity("Hyderabad");
        restaurant.setState("Telangana");
        restaurant.setPincode("500001");
        restaurant.setPhone("9999999999");
        restaurant.setEmail("dominos@test.com");
        restaurant.setImageUrl("image.png");
        restaurant.setActive(true);

        List<Restaurant> restaurants = List.of(restaurant);

        Pageable pageable = PageRequest.of(0, 5);

        Page<Restaurant> page = new PageImpl<>(
                restaurants,
                pageable,
                restaurants.size());

        when(repository.findAll(any(Pageable.class)))
                .thenReturn(page);

        RestaurantPageResponse response =
                restaurantService.getAllRestaurants(
                        0,
                        5,
                        "name",
                        "asc");

        assertNotNull(response);

        assertEquals(1, response.getContent().size());

        assertEquals("Dominos",
                response.getContent().get(0).getName());

        assertEquals(0, response.getPage());

        assertEquals(5, response.getSize());

        assertEquals(1, response.getTotalElements());

        verify(repository).findAll(any(Pageable.class));
    }
} 