package com.swiggy.swiggy_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.swiggy.swiggy_backend.dto.FavoriteResponse;
import com.swiggy.swiggy_backend.service.FavoriteService;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String addFavorite(@PathVariable Long restaurantId) {

        favoriteService.addFavorite(restaurantId);

        return "Restaurant added to favorites successfully.";
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<FavoriteResponse> getFavorites() {

        return favoriteService.getFavorites();
    }

    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String removeFavorite(@PathVariable Long restaurantId) {

        favoriteService.removeFavorite(restaurantId);

        return "Restaurant removed from favorites successfully.";
    }
}