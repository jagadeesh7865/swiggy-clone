import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api";

function Checkout() {

    const [deliveryAddress, setDeliveryAddress] = useState("");
    const [paymentMethod, setPaymentMethod] = useState("COD");
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const handlePlaceOrder = async (e) => {

        e.preventDefault();

        if (!deliveryAddress.trim()) {
            alert("Please enter your delivery address.");
            return;
        }

        try {

           

            const response = await api.post("/orders/place", {
                deliveryAddress,
                paymentMethod,
            });

            console.log("Order created:", response.data);

            alert("Order placed successfully!");

            navigate(`/orders/${response.data.orderId}`);

        } catch (error) {

            console.error("Place order error:", error);

            if (error.response?.status === 403) {
                alert("You must be logged in as a customer.");
            } else {
                alert(
                    error.response?.data?.message ||
                    "Failed to place order."
                );
            }

        } finally {

            setLoading(false);

        }
    };

    return (
        <div className="container mt-5">

            <div className="row justify-content-center">

                <div className="col-md-7">

                    <div className="card shadow">

                        <div className="card-body">

                            <h2 className="mb-4">
                                Checkout
                            </h2>

                            <form onSubmit={handlePlaceOrder}>

                                <div className="mb-4">

                                    <label className="form-label">
                                        Delivery Address
                                    </label>

                                    <textarea
                                        className="form-control"
                                        rows="4"
                                        placeholder="Enter your delivery address"
                                        value={deliveryAddress}
                                        onChange={(e) =>
                                            setDeliveryAddress(e.target.value)
                                        }
                                    />

                                </div>

                                <div className="mb-4">

                                    <label className="form-label">
                                        Payment Method
                                    </label>

                                    <select
                                        className="form-select"
                                        value={paymentMethod}
                                        onChange={(e) =>
                                            setPaymentMethod(e.target.value)
                                        }
                                    >

                                        <option value="COD">
                                            Cash on Delivery
                                        </option>

                                        <option value="ONLINE">
                                            Online Payment
                                        </option>

                                    </select>

                                </div>

                                <button
                                    type="submit"
                                    className="btn btn-danger w-100"
                                    disabled={loading}
                                >

                                    {loading
                                        ? "Placing Order..."
                                        : "Place Order"}

                                </button>

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default Checkout;