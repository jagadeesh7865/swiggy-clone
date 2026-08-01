package com.swiggy.swiggy_backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.OrderResponse;
import com.swiggy.swiggy_backend.dto.OrderStatusRequest;
import com.swiggy.swiggy_backend.dto.OrderTrackingResponse;
import com.swiggy.swiggy_backend.dto.PlaceOrderRequest;
import com.swiggy.swiggy_backend.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/place")
    public OrderResponse placeOrder(
            @RequestBody PlaceOrderRequest request) {

        return orderService.placeOrder(request);
    }
    
    @GetMapping("/{id}")
    public OrderResponse getOrderById(
            @PathVariable Long id) {

        return orderService.getOrderById(id);
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public List<OrderResponse> getMyOrders() {

        return orderService.getMyOrders();
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasAnyRole('ADMIN','DELIVERY_PARTNER')")
    public String updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequest request) {

        orderService.updateOrderStatus(orderId, request);

        return "Order status updated successfully.";
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public String cancelOrder(
            @PathVariable Long id) {

        orderService.cancelOrder(id);

        return "Order cancelled successfully";
    }
    
    @GetMapping("/{orderId}/tracking")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderTrackingResponse trackOrder(
            @PathVariable Long orderId) {

        return orderService.trackOrder(orderId);
    }
}