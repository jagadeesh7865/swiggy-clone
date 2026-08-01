package com.swiggy.swiggy_backend.service;

import java.util.List;

import com.swiggy.swiggy_backend.dto.MenuRequest;
import com.swiggy.swiggy_backend.dto.MenuResponse;

public interface MenuService {

    MenuResponse addMenuItem(Long restaurantId, MenuRequest request);

    List<MenuResponse> getMenuByRestaurant(Long restaurantId);

    MenuResponse getMenuItemById(Long id);

    MenuResponse updateMenuItem(Long id, MenuRequest request);

    void deleteMenuItem(Long id);

    List<MenuResponse> searchMenu(String keyword);
    
    void uploadMenuItemImage(Long menuItemId, String imageUrl);
}