import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getRestaurantById } from "../../services/restaurantService";
import { Link } from "react-router-dom";

function RestaurantDetails() {
  const { id } = useParams();

  const [restaurant, setRestaurant] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchRestaurant = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getRestaurantById(id);

        console.log(response.data);

        setRestaurant(response.data);
      } catch (error) {
        console.error(error);

        setError("Failed to load restaurant.");
      } finally {
        setLoading(false);
      }
    };

    fetchRestaurant();
  }, [id]);

  if (loading) {
    return <h3 className="text-center mt-5">Loading restaurant...</h3>;
  }

  if (error) {
    return <h3 className="text-center mt-5 text-danger">{error}</h3>;
  }

  if (!restaurant) {
    return <h3 className="text-center mt-5">Restaurant not found.</h3>;
  }

  return (
    <div className="container mt-5">
      <div className="card shadow">
        {restaurant.imageUrl && (
          <img
            src={restaurant.imageUrl}
            className="card-img-top"
            alt={restaurant.name}
            style={{
              height: "350px",
              objectFit: "cover",
            }}
          />
        )}

        <div className="card-body">
          <h2 className="card-title">{restaurant.name}</h2>

          <p className="card-text">{restaurant.description}</p>

          <hr />

          <p>
            <strong>Address:</strong> {restaurant.address}
          </p>

          <p>
            <strong>City:</strong> {restaurant.city}
          </p>

          <p>
            <strong>State:</strong> {restaurant.state}
          </p>

          <p>
            <strong>Pincode:</strong> {restaurant.pincode}
          </p>

          <p>
            <strong>Phone:</strong> {restaurant.phone}
          </p>

          <p>
            <strong>Email:</strong> {restaurant.email}
          </p>

          <span
            className={
              restaurant.active ? "badge bg-success" : "badge bg-danger"
            }
          >
            {restaurant.active ? "Open" : "Closed"}
          </span>

          <div className="mt-3">
            <Link
              to={`/restaurants/${restaurant.id}`}
              className="btn btn-danger"
            >
              View Details
            </Link>
          </div>

          <div className="mt-4">
            <Link
              to={`/restaurants/${restaurant.id}/menu`}
              className="btn btn-danger"
            >
              View Menu
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RestaurantDetails;
