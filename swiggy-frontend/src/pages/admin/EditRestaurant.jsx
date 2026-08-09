import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
    getRestaurantById,
    updateRestaurant,
} from "../../services/restaurantService";


function EditRestaurant() {

    const { id } = useParams();
    const navigate = useNavigate();


    const [formData, setFormData] = useState({
        name: "",
        description: "",
        address: "",
        city: "",
        state: "",
        pincode: "",
        phone: "",
        email: "",
        imageUrl: "",
    });


    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");


    useEffect(() => {

        const fetchRestaurant = async () => {

            try {

                setLoading(true);
                setError("");

                const response =
                    await getRestaurantById(id);

                console.log(
                    "Restaurant:",
                    response.data
                );

                setFormData({
                    name: response.data.name || "",
                    description:
                        response.data.description || "",
                    address:
                        response.data.address || "",
                    city:
                        response.data.city || "",
                    state:
                        response.data.state || "",
                    pincode:
                        response.data.pincode || "",
                    phone:
                        response.data.phone || "",
                    email:
                        response.data.email || "",
                    imageUrl:
                        response.data.imageUrl || "",
                });

            } catch (error) {

                console.error(
                    "Fetch restaurant error:",
                    error
                );

                setError(
                    error.response?.data?.message ||
                    "Failed to load restaurant."
                );

            } finally {

                setLoading(false);

            }
        };


        fetchRestaurant();

    }, [id]);


    const handleChange = (e) => {

        const { name, value } = e.target;

        setFormData((currentData) => ({
            ...currentData,
            [name]: value,
        }));
    };


    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            setSaving(true);
            setError("");
            setSuccess("");

            const response =
                await updateRestaurant(
                    id,
                    formData
                );

            console.log(
                "Updated restaurant:",
                response.data
            );

            setSuccess(
                "Restaurant updated successfully!"
            );

        } catch (error) {

            console.error(
                "Update restaurant error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Failed to update restaurant."
            );

        } finally {

            setSaving(false);

        }
    };


    if (loading) {

        return (
            <div className="container mt-5">
                <h4 className="text-center">
                    Loading restaurant...
                </h4>
            </div>
        );
    }


    if (error && !formData.name) {

        return (
            <div className="container mt-5">

                <div className="alert alert-danger">
                    {error}
                </div>

            </div>
        );
    }


    return (

        <div className="container mt-5 mb-5">

            <div className="row justify-content-center">

                <div className="col-md-8">

                    <div className="card shadow">

                        <div className="card-body">

                            <h2 className="text-center mb-4">
                                Edit Restaurant
                            </h2>


                            {success && (

                                <div className="alert alert-success">
                                    {success}
                                </div>

                            )}


                            {error && (

                                <div className="alert alert-danger">
                                    {error}
                                </div>

                            )}


                            <form onSubmit={handleSubmit}>

                                {/* Name */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Restaurant Name
                                    </label>

                                    <input
                                        type="text"
                                        name="name"
                                        className="form-control"
                                        value={formData.name}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                {/* Description */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Description
                                    </label>

                                    <textarea
                                        name="description"
                                        className="form-control"
                                        rows="3"
                                        value={
                                            formData.description
                                        }
                                        onChange={handleChange}
                                    />

                                </div>


                                {/* Address */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Address
                                    </label>

                                    <input
                                        type="text"
                                        name="address"
                                        className="form-control"
                                        value={formData.address}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                {/* City + State */}

                                <div className="row">

                                    <div className="col-md-6 mb-3">

                                        <label className="form-label">
                                            City
                                        </label>

                                        <input
                                            type="text"
                                            name="city"
                                            className="form-control"
                                            value={formData.city}
                                            onChange={handleChange}
                                            required
                                        />

                                    </div>


                                    <div className="col-md-6 mb-3">

                                        <label className="form-label">
                                            State
                                        </label>

                                        <input
                                            type="text"
                                            name="state"
                                            className="form-control"
                                            value={formData.state}
                                            onChange={handleChange}
                                            required
                                        />

                                    </div>

                                </div>


                                {/* Pincode + Phone */}

                                <div className="row">

                                    <div className="col-md-6 mb-3">

                                        <label className="form-label">
                                            Pincode
                                        </label>

                                        <input
                                            type="text"
                                            name="pincode"
                                            className="form-control"
                                            value={formData.pincode}
                                            onChange={handleChange}
                                            required
                                        />

                                    </div>


                                    <div className="col-md-6 mb-3">

                                        <label className="form-label">
                                            Phone
                                        </label>

                                        <input
                                            type="tel"
                                            name="phone"
                                            className="form-control"
                                            value={formData.phone}
                                            onChange={handleChange}
                                            required
                                        />

                                    </div>

                                </div>


                                {/* Email */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Email
                                    </label>

                                    <input
                                        type="email"
                                        name="email"
                                        className="form-control"
                                        value={formData.email}
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                {/* Image URL */}

                                <div className="mb-4">

                                    <label className="form-label">
                                        Image URL
                                    </label>

                                    <input
                                        type="url"
                                        name="imageUrl"
                                        className="form-control"
                                        value={formData.imageUrl}
                                        onChange={handleChange}
                                    />

                                </div>


                                <button
                                    type="submit"
                                    className="btn btn-danger w-100"
                                    disabled={saving}
                                >
                                    {saving
                                        ? "Saving..."
                                        : "Update Restaurant"}
                                </button>

                            </form>


                            <button
                                type="button"
                                className="btn btn-outline-secondary w-100 mt-3"
                                onClick={() =>
                                    navigate(
                                        "/admin/restaurants"
                                    )
                                }
                            >
                                Back to Restaurants
                            </button>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default EditRestaurant;