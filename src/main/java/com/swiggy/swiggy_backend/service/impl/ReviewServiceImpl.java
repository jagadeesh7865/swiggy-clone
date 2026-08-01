package com.swiggy.swiggy_backend.service.impl;

import java.util.List;
import java.util.stream.Stream;
import com.swiggy.swiggy_backend.entity.Role;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swiggy.swiggy_backend.dto.RatingResponse;
import com.swiggy.swiggy_backend.dto.ReviewRequest;
import com.swiggy.swiggy_backend.dto.ReviewResponse;
import com.swiggy.swiggy_backend.entity.Restaurant;
import com.swiggy.swiggy_backend.entity.Review;
import com.swiggy.swiggy_backend.entity.User;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.repository.ReviewRepository;
import com.swiggy.swiggy_backend.repository.UserRepository;
import com.swiggy.swiggy_backend.service.ReviewService;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            RestaurantRepository restaurantRepository,
            UserRepository userRepository) {

        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewResponse addReview(ReviewRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        // Temporary: fetch the first CUSTOMER user.
        // Later we'll replace this with the logged-in user from Spring Security.
        User user = userRepository.findAll().stream()
        		.filter(u -> u.getRole() == Role.CUSTOMER)
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Review review = new Review();
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setRestaurant(restaurant);
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);

        return mapToResponse(savedReview);
    }

    @Override
    public List<ReviewResponse> getReviewsByRestaurant(Long restaurantId) {

        return reviewRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RatingResponse getRestaurantRating(Long restaurantId) {

        Double average = reviewRepository.getAverageRating(restaurantId);

        Long total = reviewRepository.getTotalReviews(restaurantId);

        if (average == null) {
            average = 0.0;
        }

        if (total == null) {
            total = 0L;
        }

        return new RatingResponse(average, total);
    }

    private ReviewResponse mapToResponse(Review review) {

        ReviewResponse response = new ReviewResponse();

        response.setId(review.getId());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCustomerName(
                review.getUser().getFirstName() + " " + review.getUser().getLastName());
        response.setRestaurantName(review.getRestaurant().getName());
        response.setCreatedAt(review.getCreatedAt());

        return response;
    }
}