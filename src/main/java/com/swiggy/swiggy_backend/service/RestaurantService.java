package com.swiggy.swiggy_backend.service;

import com.swiggy.swiggy_backend.dto.RestaurantPageResponse;
import com.swiggy.swiggy_backend.dto.RestaurantRequest;
import com.swiggy.swiggy_backend.dto.RestaurantResponse;

public interface RestaurantService {

	 RestaurantResponse registerRestaurant(RestaurantRequest request);

	 RestaurantPageResponse getAllRestaurants(
		        int page,
		        int size,
		        String sortBy,
		        String direction);

	    RestaurantResponse getRestaurantById(Long id);
	    
	    RestaurantResponse updateRestaurant(Long id, RestaurantRequest request);

	    void uploadRestaurantImage(Long restaurantId, String imageUrl);

		

		

 
}