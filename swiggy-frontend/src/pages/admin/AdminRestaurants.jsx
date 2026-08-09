import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  getRestaurants,
  deactivateRestaurant,
} from "../../services/restaurantService";
function AdminRestaurants() {
  const [restaurants, setRestaurants] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchRestaurants = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await getRestaurants(page, 5);

      console.log("Admin restaurants:", response.data);

      setRestaurants(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error) {
      console.error("Admin restaurant error:", error);

      setError(error.response?.data?.message || "Failed to load restaurants.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRestaurants();
  }, [page]);

  if (loading) {
    return <h4 className="text-center mt-5">Loading restaurants...</h4>;
  }

  if (error) {
    return <h4 className="text-center mt-5 text-danger">{error}</h4>;
  }

  const handleDeactivate = async (restaurantId) => {
    const confirmed = window.confirm(
      "Are you sure you want to deactivate this restaurant?",
    );

    if (!confirmed) {
      return;
    }

    try {
      await deactivateRestaurant(restaurantId);

      alert("Restaurant deactivated successfully.");

      fetchRestaurants();
    } catch (error) {
      console.error("Deactivate restaurant error:", error);

      alert(
        error.response?.data?.message || "Failed to deactivate restaurant.",
      );
    }
  };

  return (
    <div className="container mt-5 mb-5">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>Manage Restaurants</h2>

        <Link to="/admin/restaurants/add" className="btn btn-danger">
          + Add Restaurant
        </Link>
      </div>

      {restaurants.length === 0 ? (
        <div className="alert alert-info">No restaurants found.</div>
      ) : (
        <div className="table-responsive">
          <table className="table table-bordered table-hover align-middle">
            <thead className="table-dark">
              <tr>
                <th>ID</th>
                <th>Restaurant</th>
                <th>Location</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {restaurants.map((restaurant) => (
                <tr key={restaurant.id}>
                  <td>{restaurant.id}</td>

                  <td>
                    <strong>{restaurant.name}</strong>
                  </td>

                  <td>
                    {restaurant.city}, {restaurant.state}
                  </td>

                  <td>{restaurant.phone}</td>

                  <td>{restaurant.email}</td>

                  <td>
                    <span
                      className={
                        restaurant.active
                          ? "badge bg-success"
                          : "badge bg-danger"
                      }
                    >
                      {restaurant.active ? "Active" : "Inactive"}
                    </span>
                  </td>

                  <td>
                    <Link
                      to={`/admin/restaurants/edit/${restaurant.id}`}
                      className="btn btn-sm btn-primary me-2"
                    >
                      Edit
                    </Link>

                    <Link
                      to={`/admin/restaurants/${restaurant.id}/menu`}
                      className="btn btn-sm btn-success me-2"
                    >
                      Menu
                    </Link>

                    <Link
                      to={`/restaurants/${restaurant.id}`}
                      className="btn btn-sm btn-outline-secondary"
                    >
                      View
                    </Link>

                    <button
                      className="btn btn-sm btn-outline-danger"
                      disabled={!restaurant.active}
                      onClick={() => handleDeactivate(restaurant.id)}
                    >
                      {restaurant.active ? "Deactivate" : "Inactive"}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Pagination */}

      {totalPages > 0 && (
        <div className="d-flex justify-content-center align-items-center gap-3 mt-4">
          <button
            className="btn btn-outline-danger"
            disabled={page === 0}
            onClick={() => setPage(page - 1)}
          >
            Previous
          </button>

          <span>
            Page {page + 1} of {totalPages}
          </span>

          <button
            className="btn btn-outline-danger"
            disabled={page >= totalPages - 1}
            onClick={() => setPage(page + 1)}
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
}

export default AdminRestaurants;
