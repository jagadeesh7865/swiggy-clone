package com.swiggy.swiggy_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.PaymentRequest;
import com.swiggy.swiggy_backend.dto.PaymentResponse;
import com.swiggy.swiggy_backend.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public PaymentResponse makePayment(
            @RequestBody PaymentRequest request) {

        return paymentService.makePayment(request);
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/order/{orderId}")
    public PaymentResponse getPaymentByOrder(
            @PathVariable Long orderId) {

        return paymentService.getPaymentByOrder(orderId);
    }
}