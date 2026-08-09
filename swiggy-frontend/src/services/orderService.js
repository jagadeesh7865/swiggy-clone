import api from "./api";

export const placeOrder = (orderData) => {
    return api.post("/orders/place", orderData);
};

export const getMyOrders = () => {
    return api.get("/orders/my");
};

export const getOrderById = (orderId) => {
    return api.get(`/orders/${orderId}`);
};

export const cancelOrder = (orderId) => {
    return api.delete(`/orders/${orderId}`);
};

export const trackOrder = (orderId) => {
    return api.get(`/orders/${orderId}/tracking`);
};