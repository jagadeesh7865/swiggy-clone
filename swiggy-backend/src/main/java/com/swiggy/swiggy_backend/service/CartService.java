package com.swiggy.swiggy_backend.service;

import com.swiggy.swiggy_backend.dto.AddToCartRequest;
import com.swiggy.swiggy_backend.dto.CartResponse;


public interface CartService {

    CartResponse addToCart(AddToCartRequest request);

    CartResponse getCart();

    CartResponse updateCartItem(Long cartItemId, Integer quantity);

    void removeCartItem(Long cartItemId);

    void clearCart();

}