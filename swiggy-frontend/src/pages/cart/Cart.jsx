import { useEffect, useState } from "react";
import {
  getCart,
  updateCartItem,
  removeCartItem,
} from "../../services/cartService";
import { Link } from "react-router-dom";
function Cart() {
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchCart = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getCart();

        console.log("Cart:", response.data);

        setCart(response.data);
      } catch (error) {
        console.error("Cart error:", error);

        setError("Failed to load cart.");
      } finally {
        setLoading(false);
      }
    };

    fetchCart();
  }, []);

  if (loading) {
    return <h4 className="text-center mt-5">Loading cart...</h4>;
  }

  if (error) {
    return <h4 className="text-center mt-5 text-danger">{error}</h4>;
  }

  if (!cart || cart.items.length === 0) {
    return (
      <div className="container mt-5 text-center">
        <h3>Your cart is empty 🛒</h3>
      </div>
    );
  }

  const handleQuantityChange = async (cartItemId, newQuantity) => {
    if (newQuantity < 1) {
      return;
    }

    try {
      const response = await updateCartItem(cartItemId, newQuantity);

      setCart(response.data);
    } catch (error) {
      console.error("Quantity update error:", error);

      alert("Failed to update quantity.");
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Your Cart</h2>

      {cart.items.map((item) => (
        <div key={item.id} className="card mb-3 shadow-sm">
          <div className="card-body">
            <div className="row align-items-center">
              <div className="col-md-5">
                <h5>{item.menuItemName}</h5>

                <p className="mb-0">₹{item.price} each</p>
              </div>

              <div className="col-md-3">
                <div className="d-flex align-items-center">
                  <button
                    className="btn btn-outline-secondary"
                    onClick={() =>
                      handleQuantityChange(item.id, item.quantity - 1)
                    }
                    disabled={item.quantity === 1}
                  >
                    −
                  </button>

                  <span className="mx-3">{item.quantity}</span>

                  <button
                    className="btn btn-outline-secondary"
                    onClick={() =>
                      handleQuantityChange(item.id, item.quantity + 1)
                    }
                  >
                    +
                  </button>

                  <button
                    className="btn btn-outline-danger mt-1 ms-3"
                    onClick={async () => {
                      try {
                        const response = await removeCartItem(item.id);

                        console.log(response.data);

                        const updatedCart = await getCart();

                        setCart(updatedCart.data);
                      } catch (error) {
                        console.error("Remove error:", error);

                        alert("Failed to remove item.");
                      }
                    }}
                  >
                    Remove
                  </button>
                </div>
              </div>

              <div className="col-md-4 text-md-end">
                <strong>₹{item.totalPrice}</strong>
              </div>
            </div>
          </div>
        </div>
      ))}

      <div className="card shadow">
        <div className="card-body">
          <div className="d-flex justify-content-between">
            <h4>Grand Total</h4>

            <h4>₹{cart.grandTotal}</h4>
          </div>

          <Link to="/checkout" className="btn btn-danger w-100 mt-3">
            Proceed to Checkout
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Cart;
