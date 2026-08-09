import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
    getOrderById,
    cancelOrder,
    trackOrder,
} from "../../services/orderService";

function OrderDetails() {

    const { id } = useParams();

    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const [tracking, setTracking] = useState(null);
    const [trackingLoading, setTrackingLoading] = useState(false);


    // Get order details
    useEffect(() => {

        const fetchOrder = async () => {

            try {

                setLoading(true);
                setError("");

                const response = await getOrderById(id);

                console.log("Order details:", response.data);

                setOrder(response.data);

            } catch (error) {

                console.error(
                    "Order details error:",
                    error
                );

                setError(
                    error.response?.data?.message ||
                    "Failed to load order."
                );

            } finally {

                setLoading(false);

            }
        };

        fetchOrder();

    }, [id]);


    // Get status CSS class
    const getStatusClass = (status) => {

        switch (status) {

            case "PLACED":
                return "bg-secondary";

            case "CONFIRMED":
                return "bg-primary";

            case "PREPARING":
                return "bg-warning text-dark";

            case "OUT_FOR_DELIVERY":
                return "bg-info text-dark";

            case "DELIVERED":
                return "bg-success";

            case "CANCELLED":
                return "bg-danger";

            default:
                return "bg-secondary";
        }
    };


    // Get current tracking step
    const getStatusStep = (status) => {

        const statuses = [
            "PLACED",
            "CONFIRMED",
            "PREPARING",
            "OUT_FOR_DELIVERY",
            "DELIVERED",
        ];

        return statuses.indexOf(status);
    };


    // Cancel order
    const handleCancelOrder = async () => {

        const confirmed = window.confirm(
            "Are you sure you want to cancel this order?"
        );

        if (!confirmed) {
            return;
        }

        try {

            await cancelOrder(order.orderId);

            alert("Order cancelled successfully.");

            setOrder({
                ...order,
                status: "CANCELLED",
            });

            // Clear tracking after cancellation
            setTracking(null);

        } catch (error) {

            console.error(
                "Cancel order error:",
                error
            );

            alert(
                error.response?.data?.message ||
                "Failed to cancel order."
            );
        }
    };


    // Track order
    const handleTrackOrder = async () => {

        try {

            setTrackingLoading(true);

            const response = await trackOrder(
                order.orderId
            );

            console.log(
                "Tracking:",
                response.data
            );

            setTracking(response.data);

        } catch (error) {

            console.error(
                "Tracking error:",
                error
            );

            alert(
                error.response?.data?.message ||
                "Failed to load tracking information."
            );

        } finally {

            setTrackingLoading(false);

        }
    };


    // Loading state
    if (loading) {

        return (
            <h4 className="text-center mt-5">
                Loading order...
            </h4>
        );
    }


    // Error state
    if (error) {

        return (
            <h4 className="text-center mt-5 text-danger">
                {error}
            </h4>
        );
    }


    // No order
    if (!order) {
        return null;
    }


    return (

        <div className="container mt-5">

            {/* Page title */}

            <h2 className="mb-4">
                Order #{order.orderId}
            </h2>


            {/* Order Information */}

            <div className="card shadow-sm mb-4">

                <div className="card-body">

                    <div className="d-flex justify-content-between align-items-center">

                        <h5>
                            Order Status
                        </h5>

                        <span
                            className={`badge ${getStatusClass(
                                order.status
                            )}`}
                        >
                            {order.status}
                        </span>

                    </div>

                    <hr />

                    <p>
                        <strong>
                            Order Date:
                        </strong>{" "}

                        {new Date(
                            order.orderDate
                        ).toLocaleString()}
                    </p>

                    <p>
                        <strong>
                            Delivery Address:
                        </strong>{" "}

                        {order.deliveryAddress}
                    </p>

                    <p>
                        <strong>
                            Payment Method:
                        </strong>{" "}

                        {order.paymentMethod}
                    </p>

                </div>

            </div>


            {/* Order Items */}

            <div className="card shadow-sm">

                <div className="card-body">

                    <h4 className="mb-3">
                        Items
                    </h4>

                    {order.items?.map((item) => (

                        <div
                            key={item.id}
                            className="d-flex justify-content-between border-bottom py-3"
                        >

                            <div>

                                <strong>
                                    {item.menuItemName}
                                </strong>

                                <div className="text-muted">
                                    Quantity: {item.quantity}
                                </div>

                            </div>

                            <div>

                                ₹{item.totalPrice}

                            </div>

                        </div>

                    ))}


                    {/* Total */}

                    <div className="d-flex justify-content-between mt-4">

                        <h4>
                            Total
                        </h4>

                        <h4>
                            ₹{order.totalAmount}
                        </h4>

                    </div>

                </div>

            </div>


            {/* Action Buttons */}

            <div className="mt-4">

                {order.status !== "CANCELLED" &&
                    order.status !== "DELIVERED" && (

                        <button
                            className="btn btn-outline-danger me-2"
                            onClick={handleCancelOrder}
                        >
                            Cancel Order
                        </button>

                    )}


                {order.status !== "CANCELLED" && (

                    <button
                        className="btn btn-primary me-2"
                        onClick={handleTrackOrder}
                        disabled={trackingLoading}
                    >

                        {trackingLoading
                            ? "Loading..."
                            : "Track Order"}

                    </button>

                )}


                <Link
                    to="/orders"
                    className="btn btn-outline-secondary"
                >
                    Back to My Orders
                </Link>

            </div>


            {/* Tracking Information */}

            {tracking && (

                <div className="card shadow-sm mt-4">

                    <div className="card-body">

                        <h4 className="mb-4">
                            Order Tracking
                        </h4>


                        {/* Tracking order number */}

                        <div className="mb-3">

                            <h5>
                                Order #{tracking.orderId}
                            </h5>

                            <span
                                className={`badge ${getStatusClass(
                                    tracking.status
                                )}`}
                            >
                                {tracking.status}
                            </span>

                        </div>


                        {/* Tracking progress */}

                        <div className="mt-4">

                            {[
                                "PLACED",
                                "CONFIRMED",
                                "PREPARING",
                                "OUT_FOR_DELIVERY",
                                "DELIVERED",
                            ].map((status, index) => {

                                const currentStep =
                                    getStatusStep(
                                        tracking.status
                                    );

                                const completed =
                                    index <= currentStep;

                                return (

                                    <div
                                        key={status}
                                        className="d-flex align-items-center mb-3"
                                    >

                                        <div
                                            className={`rounded-circle ${
                                                completed
                                                    ? "bg-success"
                                                    : "bg-secondary"
                                            }`}
                                            style={{
                                                width: "20px",
                                                height: "20px",
                                                flexShrink: 0,
                                            }}
                                        />

                                        <span className="ms-3">
                                            {status}
                                        </span>

                                    </div>

                                );

                            })}

                        </div>


                        {/* Backend tracking message */}

                        <div className="alert alert-info mt-4">

                            {tracking.message}

                        </div>

                    </div>

                </div>

            )}

        </div>
    );
}

export default OrderDetails;