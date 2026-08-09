package com.swiggy.swiggy_backend.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swiggy.swiggy_backend.dto.OrderItemResponse;
import com.swiggy.swiggy_backend.dto.OrderResponse;
import com.swiggy.swiggy_backend.dto.OrderStatusRequest;
import com.swiggy.swiggy_backend.dto.OrderTrackingResponse;
import com.swiggy.swiggy_backend.dto.PlaceOrderRequest;
import com.swiggy.swiggy_backend.entity.Cart;
import com.swiggy.swiggy_backend.entity.CartItem;
import com.swiggy.swiggy_backend.entity.Order;
import com.swiggy.swiggy_backend.entity.OrderItem;
import com.swiggy.swiggy_backend.entity.OrderStatus;
import com.swiggy.swiggy_backend.entity.User;
import com.swiggy.swiggy_backend.exception.ResourceNotFoundException;
import com.swiggy.swiggy_backend.repository.CartItemRepository;
import com.swiggy.swiggy_backend.repository.CartRepository;
import com.swiggy.swiggy_backend.repository.OrderItemRepository;
import com.swiggy.swiggy_backend.repository.OrderRepository;
import com.swiggy.swiggy_backend.repository.UserRepository;
import com.swiggy.swiggy_backend.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        User user = getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPaymentMethod(request.getPaymentMethod());

        double total = 0;

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setMenuItem(cartItem.getMenuItem());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getMenuItem().getPrice());

            total += cartItem.getMenuItem().getPrice()
                    * cartItem.getQuantity();

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAll(cart.getCartItems());

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return mapToResponse(savedOrder);
    }
    
    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        User user = getCurrentUser();

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(status);

        return mapToResponse(orderRepository.save(order));
    }

    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }
    @Override
    public void updateOrderStatus(Long orderId, OrderStatusRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderStatus status = OrderStatus.valueOf(request.getStatus().toUpperCase());

        order.setStatus(status);

        orderRepository.save(order);
    }
    
    @Override
    public OrderTrackingResponse trackOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        String message;

        switch (order.getStatus()) {

            case PLACED:
                message = "Your order has been placed successfully.";
                break;

            case CONFIRMED:
                message = "Restaurant has confirmed your order.";
                break;

            case PREPARING:
                message = "Your food is being prepared.";
                break;

            case OUT_FOR_DELIVERY:
                message = "Your order is out for delivery.";
                break;

            case DELIVERED:
                message = "Your order has been delivered.";
                break;

            case CANCELLED:
                message = "Your order has been cancelled.";
                break;

            default:
                message = "Order status unavailable.";
        }

        return new OrderTrackingResponse(
                order.getId(),
                order.getStatus().name(),
                message);
    }

    private OrderResponse mapToResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setDeliveryAddress(order.getDeliveryAddress());
        response.setPaymentMethod(order.getPaymentMethod());

        List<OrderItemResponse> items = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {

            OrderItemResponse dto = new OrderItemResponse();

            dto.setMenuItemId(item.getMenuItem().getId());
            dto.setMenuItemName(item.getMenuItem().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());
            dto.setTotalPrice(item.getPrice() * item.getQuantity());

            items.add(dto);
        }

        response.setItems(items);

        return response;
    }
}