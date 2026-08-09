package com.swiggy.swiggy_backend.dto;

public class AddToCartRequest {

    private Long menuItemId;
    private Integer quantity;

    public AddToCartRequest() {
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}