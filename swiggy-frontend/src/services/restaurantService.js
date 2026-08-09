import api from "./api";

export const getRestaurants = (page = 0, size = 5) => {
    return api.get("/restaurants", {
        params: {
            page,
            size,
            sortBy: "name",
            direction: "asc",
        },
    });
};



export const getRestaurantById = (id) => {
    return api.get(`/restaurants/${id}`);
};


export const createRestaurant = (restaurantData) => {
    return api.post("/restaurants", restaurantData);
};

export const updateRestaurant = (id, restaurantData) => {
    return api.put(
        `/restaurants/${id}`,
        restaurantData
    );
};

export const deactivateRestaurant = (id) => {
    return api.put(
        `/restaurants/${id}/deactivate`
    );
};