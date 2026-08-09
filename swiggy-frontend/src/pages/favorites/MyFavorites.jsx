import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getFavorites, removeFavorite } from "../../services/favoriteService";

function MyFavorites() {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Load favorites
  useEffect(() => {
    const fetchFavorites = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getFavorites();

        console.log("Favorites:", response.data);

        setFavorites(response.data);
      } catch (error) {
        console.error("Favorites error:", error);

        setError(error.response?.data?.message || "Failed to load favorites.");
      } finally {
        setLoading(false);
      }
    };

    fetchFavorites();
  }, []);

  // Remove favorite
  const handleRemoveFavorite = async (restaurantId) => {
    try {
      await removeFavorite(restaurantId);

      alert("Restaurant removed from favorites.");

      setFavorites((currentFavorites) =>
        currentFavorites.filter(
          (favorite) => favorite.restaurantId !== restaurantId,
        ),
      );
    } catch (error) {
      console.error("Remove favorite error:", error);

      alert(error.response?.data?.message || "Failed to remove favorite.");
    }
  };

  // Loading
  if (loading) {
    return <h4 className="text-center mt-5">Loading favorites...</h4>;
  }

  // Error
  if (error) {
    return <h4 className="text-center mt-5 text-danger">{error}</h4>;
  }

  return (
    <div className="container mt-5">
      <h2 className="mb-4">❤️ My Favorites</h2>

      {/* No favorites */}

      {favorites.length === 0 ? (
        <div className="text-center mt-5">
          <h4>You don't have any favorites yet.</h4>

          <p className="text-muted">
            Add your favorite restaurants to find them quickly later.
          </p>

          <Link to="/restaurants" className="btn btn-danger">
            Browse Restaurants
          </Link>
        </div>
      ) : (
        <div className="row">
          {favorites.map((favorite) => (
            <div className="col-md-6 col-lg-4 mb-4" key={favorite.restaurantId}>
              <div className="card h-100 shadow-sm">
                {/* Restaurant image */}

                {favorite.imageUrl && (
                  <img
                    src={favorite.imageUrl}
                    className="card-img-top"
                    alt={favorite.restaurantName}
                    style={{
                      height: "200px",
                      objectFit: "cover",
                    }}
                  />
                )}

                <div className="card-body">
                  <h5 className="card-title">{favorite.restaurantName}</h5>

                  <p className="card-text">{favorite.address}</p>

                  <p className="text-muted">
                    {favorite.city}, {favorite.state}
                  </p>

                  <div className="mt-3">
                    <Link
                      to={`/restaurants/${favorite.restaurantId}`}
                      className="btn btn-danger me-2"
                    >
                      View Details
                    </Link>

                    <button
                      className="btn btn-outline-danger"
                      onClick={() =>
                        handleRemoveFavorite(favorite.restaurantId)
                      }
                    >
                      Remove ❤️
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default MyFavorites;
