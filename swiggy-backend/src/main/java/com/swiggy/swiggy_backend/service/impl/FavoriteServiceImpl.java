package com.swiggy.swiggy_backend.service.impl;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.FavoriteResponse;
import com.swiggy.swiggy_backend.entity.Favorite;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.entity.User;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.FavoriteRepository;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.repository.UserRepository;
import com.swiggy.swiggy_backend.service.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public FavoriteServiceImpl(
            FavoriteRepository favoriteRepository,
            RestaurantRepository restaurantRepository,
            UserRepository userRepository) {

        this.favoriteRepository = favoriteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addFavorite(Long restaurantId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        if (favoriteRepository.existsByUserAndRestaurant(user, restaurant)) {
            throw new IllegalArgumentException("Restaurant already added to favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setRestaurant(restaurant);

        favoriteRepository.save(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(Long restaurantId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        favoriteRepository.deleteByUserAndRestaurant(user, restaurant);
    }

    @Override
    public List<FavoriteResponse> getFavorites() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return favoriteRepository.findByUser(user)
                .stream()
                .map(favorite -> new FavoriteResponse(
                        favorite.getRestaurant().getId(),
                        favorite.getRestaurant().getName(),
                        favorite.getRestaurant().getAddress(),
                        favorite.getRestaurant().getCity(),
                        favorite.getRestaurant().getState(),
                        favorite.getRestaurant().getImageUrl()))
                .collect(Collectors.toList());
    }
}