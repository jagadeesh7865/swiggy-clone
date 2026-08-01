package com.swiggy.swiggy_backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.swiggy.swiggy_backend.dto.AddToCartRequest;
import com.swiggy.swiggy_backend.dto.CartItemResponse;
import com.swiggy.swiggy_backend.dto.CartResponse;
import com.swiggy.swiggy_backend.entity.Cart;
import com.swiggy.swiggy_backend.entity.CartItem;
import com.swiggy.swiggy_backend.entity.MenuItem;
import com.swiggy.swiggy_backend.entity.User;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.CartItemRepository;
import com.swiggy.swiggy_backend.repository.CartRepository;
import com.swiggy.swiggy_backend.repository.MenuRepository;
import com.swiggy.swiggy_backend.repository.UserRepository;
import com.swiggy.swiggy_backend.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            MenuRepository menuRepository,
            UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartResponse addToCart(AddToCartRequest request) {

    	User user = getCurrentUser();

        MenuItem menuItem = menuRepository.findById(request.getMenuItemId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu item not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem existingItem = null;

        for (CartItem item : cart.getCartItems()) {
            if (item.getMenuItem().getId().equals(menuItem.getId())) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(
                    existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMenuItem(menuItem);
            cartItem.setQuantity(request.getQuantity());

            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        return mapCart(cart);
    }

    @Override
    public CartResponse getCart() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        return mapCart(cart);
    }

    @Override
    public CartResponse updateCartItem(Long cartItemId, Integer quantity) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return mapCart(cartItem.getCart());
    }
    
    private User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication Name = " + authentication.getName());

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @Override
    public void removeCartItem(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart() {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        cart.getCartItems().clear();

        cartRepository.save(cart);
    }

    private CartResponse mapCart(Cart cart) {

        CartResponse response = new CartResponse();

        response.setCartId(cart.getId());
        response.setUserId(cart.getUser().getId());

        List<CartItemResponse> items = new ArrayList<>();

        double grandTotal = 0;

        for (CartItem item : cart.getCartItems()) {

            CartItemResponse dto = new CartItemResponse();

            dto.setId(item.getId());
            dto.setMenuItemId(item.getMenuItem().getId());
            dto.setMenuItemName(item.getMenuItem().getName());
            dto.setPrice(item.getMenuItem().getPrice());
            dto.setQuantity(item.getQuantity());

            double total = item.getMenuItem().getPrice() * item.getQuantity();

            dto.setTotalPrice(total);

            grandTotal += total;

            items.add(dto);
        }

        response.setItems(items);
        response.setGrandTotal(grandTotal);

        return response;
    }
}