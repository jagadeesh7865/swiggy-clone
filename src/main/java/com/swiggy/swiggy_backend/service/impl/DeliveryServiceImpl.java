package com.swiggy.swiggy_backend.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swiggy.swiggy_backend.dto.DeliveryRequest;
import com.swiggy.swiggy_backend.dto.DeliveryResponse;
import com.swiggy.swiggy_backend.entity.Delivery;
import com.swiggy.swiggy_backend.entity.DeliveryStatus;
import com.swiggy.swiggy_backend.entity.Order;
import com.swiggy.swiggy_backend.entity.OrderStatus;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.DeliveryRepository;
import com.swiggy.swiggy_backend.repository.OrderRepository;
import com.swiggy.swiggy_backend.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    public DeliveryServiceImpl(
            DeliveryRepository deliveryRepository,
            OrderRepository orderRepository) {

        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public DeliveryResponse assignDelivery(DeliveryRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        deliveryRepository.findByOrderId(order.getId())
                .ifPresent(delivery -> {
                    throw new RuntimeException("Delivery already assigned.");
                });

        Delivery delivery = new Delivery();

        delivery.setOrder(order);
        delivery.setDeliveryPartnerName(request.getDeliveryPartnerName());
        delivery.setDeliveryPartnerPhone(request.getDeliveryPartnerPhone());
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        delivery.setAssignedAt(LocalDateTime.now());

        Delivery saved = deliveryRepository.save(delivery);

        return mapToResponse(saved);
    }

    @Override
    public DeliveryResponse getDeliveryByOrder(Long orderId) {

        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Delivery not found"));

        return mapToResponse(delivery);
    }

    @Override
    @Transactional
    public DeliveryResponse updateDeliveryStatus(
            Long deliveryId,
            DeliveryStatus status) {

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Delivery not found"));

        delivery.setStatus(status);

        if (status == DeliveryStatus.DELIVERED) {

            delivery.setDeliveredAt(LocalDateTime.now());

            Order order = delivery.getOrder();
            order.setStatus(OrderStatus.DELIVERED);
            orderRepository.save(order);
        }

        Delivery updated = deliveryRepository.save(delivery);

        return mapToResponse(updated);
    }

    private DeliveryResponse mapToResponse(Delivery delivery) {

        DeliveryResponse response = new DeliveryResponse();

        response.setDeliveryId(delivery.getId());
        response.setOrderId(delivery.getOrder().getId());
        response.setDeliveryPartnerName(delivery.getDeliveryPartnerName());
        response.setDeliveryPartnerPhone(delivery.getDeliveryPartnerPhone());
        response.setStatus(delivery.getStatus());
        response.setAssignedAt(delivery.getAssignedAt());
        response.setDeliveredAt(delivery.getDeliveredAt());

        return response;
    }
}