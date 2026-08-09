import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

import {
  getMenuByRestaurant,
  deleteMenuItem,
} from "../../services/menuService";

import { getRestaurantById } from "../../services/restaurantService";

function AdminMenu() {
  const { id } = useParams();

  const [restaurant, setRestaurant] = useState(null);
  const [menuItems, setMenuItems] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const fetchData = async () => {
    try {
      setLoading(true);
      setError("");

      const restaurantResponse = await getRestaurantById(id);

      const menuResponse = await getMenuByRestaurant(id);

      console.log("Restaurant:", restaurantResponse.data);

      console.log("Menu:", menuResponse.data);

      setRestaurant(restaurantResponse.data);

      setMenuItems(menuResponse.data);
    } catch (error) {
      console.error("Admin menu error:", error);

      setError(error.response?.data?.message || "Failed to load menu.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [id]);

  const handleDelete = async (menuItemId) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this menu item?",
    );

    if (!confirmed) {
      return;
    }

    try {
      await deleteMenuItem(menuItemId);

      alert("Menu item deleted successfully.");

      fetchData();
    } catch (error) {
      console.error("Delete menu item error:", error);

      alert(error.response?.data?.message || "Failed to delete menu item.");
    }
  };

  if (loading) {
    return (
      <div className="container mt-5">
        <h4 className="text-center">Loading menu...</h4>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container mt-5">
        <div className="alert alert-danger">{error}</div>
      </div>
    );
  }

  return (
    <div className="container mt-5 mb-5">
      {/* Restaurant heading */}

      <div className="d-flex justify-content-between align-items-center mb-4">
        <div>
          <h2>{restaurant?.name}</h2>

          <p className="text-muted mb-0">Manage Menu</p>
        </div>

        <Link
          to={`/admin/restaurants/${id}/menu/add`}
          className="btn btn-danger"
        >
          + Add Menu Item
        </Link>
      </div>

      {/* Menu */}

      {menuItems.length === 0 ? (
        <div className="alert alert-info">No menu items found.</div>
      ) : (
        <div className="row">
          {menuItems.map((item) => (
            <div className="col-md-4 mb-4" key={item.id}>
              <div className="card h-100 shadow-sm">
                {/* Image */}

                {item.imageUrl && (
                  <img
                    src={item.imageUrl}
                    className="card-img-top"
                    alt={item.name}
                    style={{
                      height: "200px",
                      objectFit: "cover",
                    }}
                  />
                )}

                <div className="card-body">
                  <div className="d-flex justify-content-between">
                    <h5 className="card-title">{item.name}</h5>

                    <span className="fw-bold">₹{item.price}</span>
                  </div>

                  <p className="text-muted">{item.category}</p>

                  <p className="card-text">{item.description}</p>

                  <span
                    className={
                      item.available ? "badge bg-success" : "badge bg-danger"
                    }
                  >
                    {item.available ? "Available" : "Unavailable"}
                  </span>
                </div>

                <div className="card-footer bg-white">
                  <Link
                    to={`/admin/menu/edit/${item.id}`}
                    className="btn btn-sm btn-primary me-2"
                  >
                    Edit
                  </Link>

                  <button
                    className="btn btn-sm btn-outline-danger"
                    onClick={() => handleDelete(item.id)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      <Link to="/admin/restaurants" className="btn btn-outline-secondary mt-3">
        ← Back to Restaurants
      </Link>
    </div>
  );
}

export default AdminMenu;
