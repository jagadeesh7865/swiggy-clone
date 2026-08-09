package com.swiggy.swiggy_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swiggy.swiggy_backend.entity.Favorite;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(User user);

    Optional<Favorite> findByUserAndRestaurant(User user, Restaurant restaurant);

    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);

    void deleteByUserAndRestaurant(User user, Restaurant restaurant);
}