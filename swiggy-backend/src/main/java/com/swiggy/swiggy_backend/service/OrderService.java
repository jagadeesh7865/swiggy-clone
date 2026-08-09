package com.swiggy.swiggy_backend.service;

import java.util.List;

import com.swiggy.swiggy_backend.dto.OrderResponse;
import com.swiggy.swiggy_backend.dto.OrderStatusRequest;
import com.swiggy.swiggy_backend.dto.OrderTrackingResponse;
import com.swiggy.swiggy_backend.dto.PlaceOrderRequest;
import com.swiggy.swiggy_backend.entity.OrderStatus;

public interface OrderService {

    OrderResponse placeOrder(PlaceOrderRequest request);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getMyOrders();

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);

    void cancelOrder(Long orderId);
    void updateOrderStatus(Long orderId, OrderStatusRequest request);

    OrderTrackingResponse trackOrder(Long orderId);

}