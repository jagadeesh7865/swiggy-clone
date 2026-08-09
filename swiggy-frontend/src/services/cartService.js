import api from "./api";

export const addToCart = (menuItemId, quantity = 1) => {
    return api.post("/cart/items", {
        menuItemId,
        quantity,
    });
};

export const getCart = () => {
    return api.get("/cart");
};

export const updateCartItem = (cartItemId, quantity) => {
    return api.put(`/cart/items/${cartItemId}`, null, {
        params: {
            quantity,
        },
    });
};

export const removeCartItem = (cartItemId) => {
    return api.delete(`/cart/items/${cartItemId}`);
};

export const clearCart = () => {
    return api.delete("/cart/clear");
};