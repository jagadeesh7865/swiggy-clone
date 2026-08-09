import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { getRestaurants } from "../../services/restaurantService";

import {
    addFavorite,
    getFavorites,
    removeFavorite,
} from "../../services/favoriteService";


function Restaurants() {

    const [restaurants, setRestaurants] = useState([]);

    const [favorites, setFavorites] = useState([]);

    const [page, setPage] = useState(0);

    const [totalPages, setTotalPages] = useState(0);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");


    useEffect(() => {

        const fetchData = async () => {

            try {

                setLoading(true);

                setError("");


                // Get restaurants

                const restaurantResponse =
                    await getRestaurants(page, 5);


                setRestaurants(
                    restaurantResponse.data.content
                );


                setTotalPages(
                    restaurantResponse.data.totalPages
                );


                // Get favorites

                const favoriteResponse =
                    await getFavorites();


                setFavorites(
                    favoriteResponse.data
                );


            } catch (error) {

                console.error(
                    "Restaurants/Favorites error:",
                    error
                );


                if (error.response?.status === 403) {

                    // User may not be logged in
                    setFavorites([]);

                } else {

                    setError(
                        "Failed to load restaurants."
                    );
                }

            } finally {

                setLoading(false);

            }
        };


        fetchData();

    }, [page]);


    // Check whether restaurant is already favorited

    const isFavorite = (restaurantId) => {

        return favorites.some(
            (favorite) =>
                favorite.restaurantId === restaurantId
        );
    };


    // Add favorite

    const handleAddFavorite = async (restaurantId) => {

        try {

            await addFavorite(restaurantId);


            const newFavorite = {
                restaurantId: restaurantId,
            };


            setFavorites((currentFavorites) => [

                ...currentFavorites,

                newFavorite,

            ]);


        } catch (error) {

            console.error(
                "Add favorite error:",
                error
            );


            alert(
                error.response?.data?.message ||
                "Failed to add favorite."
            );
        }
    };


    // Remove favorite

    const handleRemoveFavorite = async (restaurantId) => {

        try {

            await removeFavorite(restaurantId);


            setFavorites((currentFavorites) =>
                currentFavorites.filter(
                    (favorite) =>
                        favorite.restaurantId !== restaurantId
                )
            );


        } catch (error) {

            console.error(
                "Remove favorite error:",
                error
            );


            alert(
                error.response?.data?.message ||
                "Failed to remove favorite."
            );
        }
    };


    // Toggle favorite

    const handleFavoriteClick = (restaurantId) => {

        if (isFavorite(restaurantId)) {

            handleRemoveFavorite(restaurantId);

        } else {

            handleAddFavorite(restaurantId);

        }
    };


    // Loading

    if (loading) {

        return (
            <h4 className="text-center mt-5">
                Loading restaurants...
            </h4>
        );
    }


    // Error

    if (error) {

        return (
            <h4 className="text-center mt-5 text-danger">
                {error}
            </h4>
        );
    }


    return (

        <div className="container mt-5">

            <h2 className="mb-4">
                Restaurants
            </h2>


            <div className="row">

                {restaurants.length === 0 ? (

                    <div className="col-12 text-center">

                        <h5>
                            No restaurants found.
                        </h5>

                    </div>

                ) : (

                    restaurants.map((restaurant) => {

                        const favorite =
                            isFavorite(restaurant.id);


                        return (

                            <div
                                className="col-md-4 mb-4"
                                key={restaurant.id}
                            >

                                <div className="card h-100 shadow-sm">


                                    {restaurant.imageUrl && (

                                        <img
                                            src={restaurant.imageUrl}
                                            className="card-img-top"
                                            alt={restaurant.name}
                                            style={{
                                                height: "200px",
                                                objectFit: "cover",
                                            }}
                                        />

                                    )}


                                    <div className="card-body">

                                        <h5 className="card-title">
                                            {restaurant.name}
                                        </h5>


                                        <p className="card-text">
                                            {restaurant.description}
                                        </p>


                                        <p className="text-muted">
                                            {restaurant.city},{" "}
                                            {restaurant.state}
                                        </p>


                                        <span
                                            className={
                                                restaurant.active
                                                    ? "badge bg-success"
                                                    : "badge bg-danger"
                                            }
                                        >
                                            {restaurant.active
                                                ? "Open"
                                                : "Closed"}
                                        </span>


                                        <div className="mt-3">


                                            <Link
                                                to={`/restaurants/${restaurant.id}`}
                                                className="btn btn-danger me-2"
                                            >
                                                View Details
                                            </Link>


                                            <button
                                                className={
                                                    favorite
                                                        ? "btn btn-danger"
                                                        : "btn btn-outline-danger"
                                                }
                                                onClick={() =>
                                                    handleFavoriteClick(
                                                        restaurant.id
                                                    )
                                                }
                                            >

                                                {favorite
                                                    ? "❤️ Favorited"
                                                    : "❤️ Favorite"}

                                            </button>


                                        </div>

                                    </div>

                                </div>

                            </div>

                        );

                    })

                )}

            </div>


            {/* Pagination */}

            {totalPages > 0 && (

                <div className="d-flex justify-content-center align-items-center gap-3 mb-5">


                    <button
                        className="btn btn-outline-danger"
                        disabled={page === 0}
                        onClick={() =>
                            setPage(page - 1)
                        }
                    >
                        Previous
                    </button>


                    <span>
                        Page {page + 1} of {totalPages}
                    </span>


                    <button
                        className="btn btn-outline-danger"
                        disabled={
                            page >= totalPages - 1
                        }
                        onClick={() =>
                            setPage(page + 1)
                        }
                    >
                        Next
                    </button>


                </div>

            )}

        </div>
    );
}


export default Restaurants;