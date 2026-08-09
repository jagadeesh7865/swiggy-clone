import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getMenuByRestaurant } from "../../services/menuService";
import { addToCart } from "../../services/cartService";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { getRestaurantById } from "../../services/restaurantService";
import { getRestaurants } from "../../services/restaurantService";
import { Navigate } from "react-router-dom";
import ProtectedRoute from "../../routes/ProtectedRoute";
import Restaurants from "./Restaurants";
import RestaurantDetails from "./RestaurantDetails";
import { BrowserRouter, Routes, Route } from "react-router-dom";


function MenuItems() {
  const { id } = useParams();

  const [menuItems, setMenuItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchMenu = async () => {
      try {
        

        const response = await getMenuByRestaurant(id);

        console.log("Menu response:", response.data);

        setMenuItems(response.data);
      } catch (error) {
        console.error("Menu error:", error);

        setError("Failed to load menu.");
      } finally {
        setLoading(false);
      }
    };

    fetchMenu();
  }, [id]);

  if (loading) {
    return <h4 className="text-center mt-4">Loading menu...</h4>;
  }

  if (error) {
    return <h4 className="text-center mt-4 text-danger">{error}</h4>;
  }

  const handleAddToCart = async (menuItemId) => {
    try {
      const response = await addToCart(menuItemId, 1);

      console.log("Cart updated:", response.data);

      alert("Item added to cart!");
    } catch (error) {
      console.error("Add to cart error:", error);

      if (error.response?.status === 403) {
        alert("Please login as a customer.");
      } else {
        alert("Failed to add item to cart.");
      }
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Menu</h2>

      <div className="row">
        {menuItems.map((item) => (
          <div className="col-md-4 mb-4" key={item.id}>
            <div className="card h-100 shadow-sm">
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
                <h5 className="card-title">{item.name}</h5>

                <p className="card-text">{item.description}</p>

                <p>
                  <strong>Category:</strong> {item.category}
                </p>

                <h5>₹{item.price}</h5>

                <span
                  className={
                    item.available ? "badge bg-success" : "badge bg-danger"
                  }
                >
                  {item.available ? "Available" : "Unavailable"}
                </span>
                {item.available && (
                  <button
                    className="btn btn-danger mt-3 w-100"
                    onClick={() => handleAddToCart(item.id)}
                  >
                    Add to Cart
                  </button>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MenuItems;
