package com.swiggy.swiggy_backend.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.ApplyCouponRequest;
import com.swiggy.swiggy_backend.dto.ApplyCouponResponse;
import com.swiggy.swiggy_backend.dto.CouponRequest;
import com.swiggy.swiggy_backend.dto.CouponResponse;
import com.swiggy.swiggy_backend.entity.Coupon;
import com.swiggy.swiggy_backend.repository.CouponRepository;
import com.swiggy.swiggy_backend.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public CouponResponse createCoupon(CouponRequest request) {

        Coupon coupon = new Coupon();

        coupon.setCode(request.getCode());
        coupon.setDiscountPercentage(request.getDiscountPercentage());
        coupon.setMinimumOrderAmount(request.getMinimumOrderAmount());
        coupon.setExpiryDate(request.getExpiryDate());
        coupon.setActive(request.isActive());

        coupon = couponRepository.save(coupon);

        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscountPercentage(),
                coupon.getMinimumOrderAmount(),
                coupon.getExpiryDate(),
                coupon.isActive());
    }

    @Override
    public List<CouponResponse> getAllCoupons() {

        return couponRepository.findAll()
                .stream()
                .map(coupon -> new CouponResponse(
                        coupon.getId(),
                        coupon.getCode(),
                        coupon.getDiscountPercentage(),
                        coupon.getMinimumOrderAmount(),
                        coupon.getExpiryDate(),
                        coupon.isActive()))
                .collect(Collectors.toList());
    }

    @Override
    public ApplyCouponResponse applyCoupon(ApplyCouponRequest request) {

        Coupon coupon = couponRepository.findByCode(request.getCouponCode())
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        if (!coupon.isActive()) {
            throw new RuntimeException("Coupon is inactive");
        }

        if (coupon.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Coupon has expired");
        }

        if (request.getOrderAmount() < coupon.getMinimumOrderAmount()) {
            throw new RuntimeException(
                    "Minimum order amount is " + coupon.getMinimumOrderAmount());
        }

        double discount =
                request.getOrderAmount() * coupon.getDiscountPercentage() / 100;

        double finalAmount = request.getOrderAmount() - discount;

        return new ApplyCouponResponse(
                request.getOrderAmount(),
                discount,
                finalAmount,
                "Coupon applied successfully");
    }
}