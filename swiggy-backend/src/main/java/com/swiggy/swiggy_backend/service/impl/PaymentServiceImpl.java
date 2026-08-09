package com.swiggy.swiggy_backend.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swiggy.swiggy_backend.dto.PaymentRequest;
import com.swiggy.swiggy_backend.dto.PaymentResponse;
import com.swiggy.swiggy_backend.entity.Order;
import com.swiggy.swiggy_backend.entity.OrderStatus;
import com.swiggy.swiggy_backend.entity.Payment;
import com.swiggy.swiggy_backend.entity.PaymentStatus;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.OrderRepository;
import com.swiggy.swiggy_backend.repository.PaymentRepository;
import com.swiggy.swiggy_backend.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public PaymentResponse makePayment(PaymentRequest request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        paymentRepository.findByOrderId(order.getId())
                .ifPresent(payment -> {
                    throw new RuntimeException("Payment already completed for this order");
                });

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(order.getPaymentMethod());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByOrder(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getId());
        response.setOrderId(payment.getOrder().getId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getStatus());
        response.setPaymentDate(payment.getPaymentDate());

        return response;
    }
}