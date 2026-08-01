package com.swiggy.swiggy_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.SearchResponse;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    private final RestaurantRepository restaurantRepository;

    public SearchServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<SearchResponse> searchRestaurants(String keyword) {

        List<Restaurant> restaurants = restaurantRepository.searchRestaurants(keyword);

        return restaurants.stream()
                .map(r -> new SearchResponse(
                        r.getId(),
                        r.getName(),
                        r.getDescription(),
                        r.getAddress(),
                        r.getCity(),
                        r.getState()))
                .collect(Collectors.toList());
    }
}