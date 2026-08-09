package com.swiggy.swiggy_backend.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.RestaurantPageResponse;
import com.swiggy.swiggy_backend.dto.RestaurantRequest;
import com.swiggy.swiggy_backend.dto.RestaurantResponse;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.service.RestaurantService;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void uploadRestaurantImage(Long restaurantId, String imageUrl) {

        Restaurant restaurant = repository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setImageUrl(imageUrl);

        repository.save(restaurant);
    }
   
   
    @Override
    public RestaurantPageResponse getAllRestaurants(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Restaurant> restaurants = repository.findAll(pageable);

        List<RestaurantResponse> list = restaurants
                .getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return new RestaurantPageResponse(
                list,
                restaurants.getNumber(),
                restaurants.getSize(),
                restaurants.getTotalElements(),
                restaurants.getTotalPages(),
                restaurants.isLast()
        );
    }
    
    @Cacheable(value = "restaurant", key = "#id")
    @Override
    public RestaurantResponse getRestaurantById(Long id) {

        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        return mapToResponse(restaurant);
    }
    
    @CacheEvict(value = {"restaurants", "restaurant"}, allEntries = true)
    @Override
    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest request) {

        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setCity(request.getCity());
        restaurant.setState(request.getState());
        restaurant.setPincode(request.getPincode());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());
        restaurant.setImageUrl(request.getImageUrl());

        Restaurant updated = repository.save(restaurant);

        return mapToResponse(updated);
    }

    
    private RestaurantResponse mapToResponse(Restaurant restaurant) {

        RestaurantResponse response = new RestaurantResponse();

        response.setId(restaurant.getId());
        response.setName(restaurant.getName());
        response.setDescription(restaurant.getDescription());
        response.setAddress(restaurant.getAddress());
        response.setCity(restaurant.getCity());
        response.setState(restaurant.getState());
        response.setPincode(restaurant.getPincode());
        response.setPhone(restaurant.getPhone());
        response.setEmail(restaurant.getEmail());
        response.setImageUrl(restaurant.getImageUrl());
        response.setActive(restaurant.isActive());

        return response;
    }
    
    @CacheEvict(value = {"restaurants", "restaurant"}, allEntries = true)
    @Override
    public RestaurantResponse registerRestaurant(RestaurantRequest request) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setAddress(request.getAddress());
        restaurant.setCity(request.getCity());
        restaurant.setState(request.getState());
        restaurant.setPincode(request.getPincode());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());
        restaurant.setImageUrl(request.getImageUrl());

        Restaurant saved = repository.save(restaurant);

        return mapToResponse(saved);
    }
    
    
    
    
	
}