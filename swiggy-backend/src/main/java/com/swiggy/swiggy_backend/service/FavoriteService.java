package com.swiggy.swiggy_backend.service;

import java.util.List;

import com.swiggy.swiggy_backend.dto.FavoriteResponse;

public interface FavoriteService {

    void addFavorite(Long restaurantId);

    void removeFavorite(Long restaurantId);

    List<FavoriteResponse> getFavorites();
}