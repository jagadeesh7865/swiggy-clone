import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import { addMenuItem } from "../../services/menuService";

function AddMenuItem() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: "",
        description: "",
        price: "",
        category: "",
        imageUrl: "",
        available: true,
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");


    const handleChange = (e) => {

        const { name, value, type, checked } = e.target;

        setFormData((currentData) => ({
            ...currentData,
            [name]: type === "checkbox"
                ? checked
                : value,
        }));
    };


    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            setLoading(true);
            setError("");
            setSuccess("");

            const menuData = {
                name: formData.name,
                description: formData.description,
                price: Number(formData.price),
                category: formData.category,
                imageUrl: formData.imageUrl,
                available: formData.available,
            };

            const response = await addMenuItem(
                id,
                menuData
            );

            console.log(
                "Menu item created:",
                response.data
            );

            setSuccess(
                "Menu item added successfully!"
            );

            setTimeout(() => {

                navigate(
                    `/admin/restaurants/${id}/menu`
                );

            }, 1000);

        } catch (error) {

            console.error(
                "Add menu item error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Failed to add menu item."
            );

        } finally {

            setLoading(false);

        }
    };


    return (

        <div className="container mt-5 mb-5">

            <div className="row justify-content-center">

                <div className="col-md-8">

                    <div className="card shadow">

                        <div className="card-body">

                            <h2 className="text-center mb-4">
                                Add Menu Item
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
                                        Menu Item Name
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


                                {/* Price */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Price
                                    </label>

                                    <input
                                        type="number"
                                        name="price"
                                        className="form-control"
                                        value={formData.price}
                                        onChange={handleChange}
                                        min="0"
                                        step="0.01"
                                        required
                                    />

                                </div>


                                {/* Category */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Category
                                    </label>

                                    <input
                                        type="text"
                                        name="category"
                                        className="form-control"
                                        value={formData.category}
                                        onChange={handleChange}
                                        placeholder="Biryani"
                                        required
                                    />

                                </div>


                                {/* Image URL */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Image URL
                                    </label>

                                    <input
                                        type="url"
                                        name="imageUrl"
                                        className="form-control"
                                        value={formData.imageUrl}
                                        onChange={handleChange}
                                        placeholder="https://example.com/image.jpg"
                                    />

                                </div>


                                {/* Available */}

                                <div className="form-check mb-4">

                                    <input
                                        type="checkbox"
                                        name="available"
                                        className="form-check-input"
                                        id="available"
                                        checked={
                                            formData.available
                                        }
                                        onChange={handleChange}
                                    />

                                    <label
                                        className="form-check-label"
                                        htmlFor="available"
                                    >
                                        Available
                                    </label>

                                </div>


                                {/* Submit */}

                                <button
                                    type="submit"
                                    className="btn btn-danger w-100"
                                    disabled={loading}
                                >

                                    {loading
                                        ? "Adding..."
                                        : "Add Menu Item"}

                                </button>

                            </form>


                            <button
                                type="button"
                                className="btn btn-outline-secondary w-100 mt-3"
                                onClick={() =>
                                    navigate(
                                        `/admin/restaurants/${id}/menu`
                                    )
                                }
                            >
                                Back to Menu
                            </button>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default AddMenuItem;