package com.swiggy.swiggy_backend.service.impl;

import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.DashboardResponse;
import com.swiggy.swiggy_backend.repository.OrderRepository;
import com.swiggy.swiggy_backend.repository.RestaurantRepository;
import com.swiggy.swiggy_backend.repository.UserRepository;
import com.swiggy.swiggy_backend.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    public DashboardServiceImpl(UserRepository userRepository,
                                RestaurantRepository restaurantRepository,
                                OrderRepository orderRepository) {

        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public DashboardResponse getDashboard() {

        return new DashboardResponse(
                userRepository.count(),
                restaurantRepository.count(),
                orderRepository.getTotalOrders(),
                orderRepository.getTotalRevenue(),
                orderRepository.getDeliveredOrders(),
                orderRepository.getCancelledOrders()
        );
    }
}