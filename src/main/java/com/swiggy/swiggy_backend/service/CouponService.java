package com.swiggy.swiggy_backend.service;

import java.util.List;

import com.swiggy.swiggy_backend.dto.ApplyCouponRequest;
import com.swiggy.swiggy_backend.dto.ApplyCouponResponse;
import com.swiggy.swiggy_backend.dto.CouponRequest;
import com.swiggy.swiggy_backend.dto.CouponResponse;

public interface CouponService {

    CouponResponse createCoupon(CouponRequest request);

    List<CouponResponse> getAllCoupons();

    ApplyCouponResponse applyCoupon(ApplyCouponRequest request);

}