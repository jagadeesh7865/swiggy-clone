package com.swiggy.swiggy_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.swiggy_backend.dto.AddToCartRequest;
import com.swiggy.swiggy_backend.dto.CartResponse;
import com.swiggy.swiggy_backend.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/items")
    public CartResponse addToCart(@RequestBody AddToCartRequest request) {
        return cartService.addToCart(request);
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public CartResponse getCart() {
        return cartService.getCart();
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/items/{cartItemId}")
    public CartResponse updateCartItem(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {

        return cartService.updateCartItem(cartItemId, quantity);
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/items/{cartItemId}")
    public String removeCartItem(@PathVariable Long cartItemId) {

        cartService.removeCartItem(cartItemId);

        return "Cart item removed successfully";
    }
    
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "Cart cleared successfully";
    }
}