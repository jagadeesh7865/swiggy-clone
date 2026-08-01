package com.swiggy.swiggy_backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.MenuRequest;
import com.swiggy.swiggy_backend.dto.MenuResponse;
import com.swiggy.swiggy_backend.service.MenuService;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    @PostMapping("/api/restaurants/{restaurantId}/menu")
    public MenuResponse addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuRequest request) {

        return menuService.addMenuItem(restaurantId, request);
    }

    @GetMapping("/api/restaurants/{restaurantId}/menu")
    public List<MenuResponse> getMenuByRestaurant(
            @PathVariable Long restaurantId) {

        return menuService.getMenuByRestaurant(restaurantId);
    }

    @GetMapping("/api/menu/{id}")
    public MenuResponse getMenuItemById(
            @PathVariable Long id) {

        return menuService.getMenuItemById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    @PutMapping("/api/menu/{id}")
    public MenuResponse updateMenuItem(
            @PathVariable Long id,
            @RequestBody MenuRequest request) {

        return menuService.updateMenuItem(id, request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RESTAURANT_OWNER')")
    @DeleteMapping("/api/menu/{id}")
    public String deleteMenuItem(
            @PathVariable Long id) {

        menuService.deleteMenuItem(id);
        return "Menu item deleted successfully";
    }

    @GetMapping("/api/menu/search")
    public List<MenuResponse> searchMenu(
            @RequestParam String keyword) {

        return menuService.searchMenu(keyword);
    }
}