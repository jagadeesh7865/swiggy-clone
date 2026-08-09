package com.swiggy.swiggy_backend.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swiggy.swiggy_backend.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
    
    @Query("SELECT COUNT(o) FROM Order o")
    Long getTotalOrders();

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o")
    Double getTotalRevenue();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = com.swiggy.swiggy_backend.entity.OrderStatus.DELIVERED")
    Long getDeliveredOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = com.swiggy.swiggy_backend.entity.OrderStatus.CANCELLED")
    Long getCancelledOrders();

}