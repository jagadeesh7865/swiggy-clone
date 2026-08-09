
import api from "./api";

export const addFavorite = (restaurantId) => {
    return api.post(`/favorites/${restaurantId}`);
};

export const getFavorites = () => {
    return api.get("/favorites");
};

export const removeFavorite = (restaurantId) => {
    return api.delete(`/favorites/${restaurantId}`);
};