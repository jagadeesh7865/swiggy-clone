package com.swiggy.swiggy_backend.dto;

import java.time.LocalDate;

public class CouponResponse {

    private Long id;
    private String code;
    private Double discountPercentage;
    private Double minimumOrderAmount;
    private LocalDate expiryDate;
    private boolean active;

    public CouponResponse() {
    }

    public CouponResponse(Long id, String code,
                          Double discountPercentage,
                          Double minimumOrderAmount,
                          LocalDate expiryDate,
                          boolean active) {
        this.id = id;
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.minimumOrderAmount = minimumOrderAmount;
        this.expiryDate = expiryDate;
        this.active = active;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Double getMinimumOrderAmount() {
		return minimumOrderAmount;
	}

	public void setMinimumOrderAmount(Double minimumOrderAmount) {
		this.minimumOrderAmount = minimumOrderAmount;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

    // Generate manual getters and setters
}