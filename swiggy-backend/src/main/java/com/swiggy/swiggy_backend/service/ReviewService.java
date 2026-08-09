package com.swiggy.swiggy_backend.service;

import java.util.List;

import com.swiggy.swiggy_backend.dto.RatingResponse;
import com.swiggy.swiggy_backend.dto.ReviewRequest;
import com.swiggy.swiggy_backend.dto.ReviewResponse;

public interface ReviewService {

    ReviewResponse addReview(ReviewRequest request);

    List<ReviewResponse> getReviewsByRestaurant(Long restaurantId);

    RatingResponse getRestaurantRating(Long restaurantId);
}