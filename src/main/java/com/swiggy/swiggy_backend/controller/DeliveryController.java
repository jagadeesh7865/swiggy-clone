package com.swiggy.swiggy_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.DeliveryRequest;
import com.swiggy.swiggy_backend.dto.DeliveryResponse;
import com.swiggy.swiggy_backend.entity.DeliveryStatus;
import com.swiggy.swiggy_backend.service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DeliveryResponse assignDelivery(
            @RequestBody DeliveryRequest request) {

        return deliveryService.assignDelivery(request);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'DELIVERY_PARTNER')")
    @GetMapping("/order/{orderId}")
    public DeliveryResponse getDeliveryByOrder(
            @PathVariable Long orderId) {

        return deliveryService.getDeliveryByOrder(orderId);
    }
    
    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @PutMapping("/{deliveryId}/status")
    public DeliveryResponse updateDeliveryStatus(
            @PathVariable Long deliveryId,
            @RequestParam DeliveryStatus status) {

        return deliveryService.updateDeliveryStatus(deliveryId, status);
    }
}