package com.swiggy.swiggy_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.MenuRequest;
import com.swiggy.swiggy_backend.dto.MenuResponse;
import com.swiggy.swiggy_backend.entity.MenuItem;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.MenuRepository;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuServiceImpl(MenuRepository menuRepository,
                           RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public MenuResponse addMenuItem(Long restaurantId, MenuRequest request) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem menuItem = new MenuItem();

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(request.getCategory());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setAvailable(request.isAvailable());
        menuItem.setRestaurant(restaurant);

        MenuItem saved = menuRepository.save(menuItem);

        return mapToResponse(saved);
    }

    @Override
    public List<MenuResponse> getMenuByRestaurant(Long restaurantId) {

        return menuRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuResponse getMenuItemById(Long id) {

        MenuItem menuItem = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        return mapToResponse(menuItem);
    }

    @Override
    public MenuResponse updateMenuItem(Long id, MenuRequest request) {

        MenuItem menuItem = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setCategory(request.getCategory());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setAvailable(request.isAvailable());

        MenuItem updated = menuRepository.save(menuItem);

        return mapToResponse(updated);
    }
    
    @Override
    public void uploadMenuItemImage(Long menuItemId, String imageUrl) {

        MenuItem menuItem = menuRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu Item not found"));

        menuItem.setImageUrl(imageUrl);

        menuRepository.save(menuItem);
    }

    @Override
    public void deleteMenuItem(Long id) {

        MenuItem menuItem = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuRepository.delete(menuItem);
    }

    @Override
    public List<MenuResponse> searchMenu(String keyword) {

        return menuRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MenuResponse mapToResponse(MenuItem menuItem) {

        MenuResponse response = new MenuResponse();

        response.setId(menuItem.getId());
        response.setName(menuItem.getName());
        response.setDescription(menuItem.getDescription());
        response.setPrice(menuItem.getPrice());
        response.setCategory(menuItem.getCategory());
        response.setImageUrl(menuItem.getImageUrl()); 
        response.setAvailable(menuItem.isAvailable());
        response.setRestaurantId(menuItem.getRestaurant().getId());

        return response;
    }
}