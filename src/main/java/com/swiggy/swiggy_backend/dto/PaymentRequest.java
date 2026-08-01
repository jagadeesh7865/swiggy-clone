package com.swiggy.swiggy_backend.dto;

public class PaymentRequest {

    private Long orderId;

    public PaymentRequest() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}