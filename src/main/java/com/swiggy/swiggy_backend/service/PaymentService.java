package com.swiggy.swiggy_backend.service;

import com.swiggy.swiggy_backend.dto.PaymentRequest;
import com.swiggy.swiggy_backend.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest request);

    PaymentResponse getPaymentByOrder(Long orderId);

}