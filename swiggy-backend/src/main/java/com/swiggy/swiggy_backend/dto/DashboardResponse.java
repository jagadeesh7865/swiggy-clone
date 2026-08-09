package com.swiggy.swiggy_backend.dto;

public class DashboardResponse {

    private Long totalUsers;
    private Long totalRestaurants;
    private Long totalOrders;
    private Double totalRevenue;
    private Long deliveredOrders;
    private Long cancelledOrders;

    public DashboardResponse() {
    }

    public DashboardResponse(Long totalUsers,
                             Long totalRestaurants,
                             Long totalOrders,
                             Double totalRevenue,
                             Long deliveredOrders,
                             Long cancelledOrders) {
        this.totalUsers = totalUsers;
        this.totalRestaurants = totalRestaurants;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.deliveredOrders = deliveredOrders;
        this.cancelledOrders = cancelledOrders;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalRestaurants() {
        return totalRestaurants;
    }

    public void setTotalRestaurants(Long totalRestaurants) {
        this.totalRestaurants = totalRestaurants;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getDeliveredOrders() {
        return deliveredOrders;
    }

    public void setDeliveredOrders(Long deliveredOrders) {
        this.deliveredOrders = deliveredOrders;
    }

    public Long getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(Long cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }
}