import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getMyOrders } from "../../services/orderService";

function MyOrders() {

    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        const fetchOrders = async () => {

            try {
                
                setLoading(true);
                setError("");

                const response = await getMyOrders();

                console.log("My orders:", response.data);

                setOrders(response.data);

            } catch (error) {

                console.error("Orders error:", error);

                setError(
                    error.response?.data?.message ||
                    "Failed to load orders."
                );

            } finally {

                setLoading(false);

            }
        };

        fetchOrders();

    }, []);

    if (loading) {
        return (
            <h4 className="text-center mt-5">
                Loading orders...
            </h4>
        );
    }

    if (error) {
        return (
            <h4 className="text-center mt-5 text-danger">
                {error}
            </h4>
        );
    }

    if (orders.length === 0) {
        return (
            <div className="container mt-5 text-center">
                <h3>No orders yet 📦</h3>

                <Link
                    to="/restaurants"
                    className="btn btn-danger mt-3"
                >
                    Browse Restaurants
                </Link>
            </div>
        );
    }

    return (
        <div className="container mt-5">

            <h2 className="mb-4">
                My Orders
            </h2>

            {orders.map((order) => (

                <div
                    className="card shadow-sm mb-4"
                    key={order.orderId}
                >

                    <div className="card-body">

                        <div className="d-flex justify-content-between align-items-center">

                            <div>
                                <h5>
                                    Order #{order.orderId}
                                </h5>

                                <small className="text-muted">
                                    {new Date(
                                        order.orderDate
                                    ).toLocaleString()}
                                </small>
                            </div>

                            <span className="badge bg-warning text-dark">
                                {order.status}
                            </span>

                        </div>

                        <hr />

                        <p>
                            <strong>Delivery Address:</strong>{" "}
                            {order.deliveryAddress}
                        </p>

                        <p>
                            <strong>Payment:</strong>{" "}
                            {order.paymentMethod}
                        </p>

                        <h5>
                            Total: ₹{order.totalAmount}
                        </h5>

                        <div className="mt-3">

                            <Link
                                to={`/orders/${order.orderId}`}
                                className="btn btn-outline-danger"
                            >
                                View Details
                            </Link>

                        </div>

                    </div>

                </div>

            ))}

        </div>
    );
}

export default MyOrders;