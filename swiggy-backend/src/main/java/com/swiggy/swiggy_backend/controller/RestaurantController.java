package com.swiggy.swiggy_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.RestaurantPageResponse;
import com.swiggy.swiggy_backend.dto.RestaurantRequest;
import com.swiggy.swiggy_backend.dto.RestaurantResponse;
import com.swiggy.swiggy_backend.service.RestaurantService;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService service;
    
   
    
    @Autowired
    private CacheManager cacheManager;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public RestaurantResponse registerRestaurant(
            @Validated
            @RequestBody RestaurantRequest request) {

        return service.registerRestaurant(request);
    }
    
    @GetMapping
    public ResponseEntity<RestaurantPageResponse> getAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                service.getAllRestaurants(page, size, sortBy, direction));
    }
    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable Long id) {
        return service.getRestaurantById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public RestaurantResponse updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantRequest request) {

        return service.updateRestaurant(id, request);
    }
    
    @GetMapping("/me")
    public String me(Authentication authentication) {
        return authentication.getAuthorities().toString();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/deactivate")
    public RestaurantResponse deactivateRestaurant(
            @PathVariable Long id) {

        return service.deactivateRestaurant(id);
    }

    
    

}