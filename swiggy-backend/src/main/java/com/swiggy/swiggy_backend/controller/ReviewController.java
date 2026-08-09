package com.swiggy.swiggy_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.swiggy.swiggy_backend.dto.RatingResponse;
import com.swiggy.swiggy_backend.dto.ReviewRequest;
import com.swiggy.swiggy_backend.dto.ReviewResponse;
import com.swiggy.swiggy_backend.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

      
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse addReview(
            @Valid @RequestBody ReviewRequest request) {

        return reviewService.addReview(request);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<ReviewResponse> getReviewsByRestaurant(
            @PathVariable Long restaurantId) {

        return reviewService.getReviewsByRestaurant(restaurantId);
    }

    @GetMapping("/restaurant/{restaurantId}/rating")
    public RatingResponse getRestaurantRating(
            @PathVariable Long restaurantId) {

        return reviewService.getRestaurantRating(restaurantId);
    }
}