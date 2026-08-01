package com.swiggy.swiggy_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.swiggy.swiggy_backend.dto.ApplyCouponRequest;
import com.swiggy.swiggy_backend.dto.ApplyCouponResponse;
import com.swiggy.swiggy_backend.dto.CouponRequest;
import com.swiggy.swiggy_backend.dto.CouponResponse;
import com.swiggy.swiggy_backend.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CouponResponse createCoupon(@RequestBody CouponRequest request) {

        return couponService.createCoupon(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    public List<CouponResponse> getAllCoupons() {

        return couponService.getAllCoupons();
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ApplyCouponResponse applyCoupon(
            @RequestBody ApplyCouponRequest request) {

        return couponService.applyCoupon(request);
    }
}