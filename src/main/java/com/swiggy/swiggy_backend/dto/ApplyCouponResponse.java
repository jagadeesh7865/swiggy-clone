package com.swiggy.swiggy_backend.dto;

public class ApplyCouponResponse {

    private Double originalAmount;
    private Double discount;
    private Double finalAmount;
    private String message;

    public ApplyCouponResponse() {
    }

    public ApplyCouponResponse(Double originalAmount,
                               Double discount,
                               Double finalAmount,
                               String message) {
        this.originalAmount = originalAmount;
        this.discount = discount;
        this.finalAmount = finalAmount;
        this.message = message;
    }

    public Double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}