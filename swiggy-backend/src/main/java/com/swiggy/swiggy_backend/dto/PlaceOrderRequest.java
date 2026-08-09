package com.swiggy.swiggy_backend.dto;

public class PlaceOrderRequest {

    private String deliveryAddress;

    private String paymentMethod;

    public PlaceOrderRequest() {
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}