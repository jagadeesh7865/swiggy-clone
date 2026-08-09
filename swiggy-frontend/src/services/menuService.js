import api from "./api";


export const getMenuByRestaurant = (restaurantId) => {
    return api.get(
        `/restaurants/${restaurantId}/menu`
    );
};


export const getMenuItemById = (id) => {
    return api.get(`/menu/${id}`);
};


export const addMenuItem = (
    restaurantId,
    menuData
) => {
    return api.post(
        `/restaurants/${restaurantId}/menu`,
        menuData
    );
};


export const updateMenuItem = (
    id,
    menuData
) => {
    return api.put(
        `/menu/${id}`,
        menuData
    );
};


export const deleteMenuItem = (id) => {
    return api.delete(
        `/menu/${id}`
    );
};


export const searchMenu = (keyword) => {
    return api.get(
        `/menu/search?keyword=${encodeURIComponent(keyword)}`
    );
};