import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
    getMenuItemById,
    updateMenuItem,
} from "../../services/menuService";

function EditMenuItem() {

    const { id } = useParams();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
    name: "",
    description: "",
    price: "",
    category: "",
    imageUrl: "",
    available: true,
    restaurantId: null,
});

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {

        const fetchMenuItem = async () => {

            try {

                setLoading(true);
                setError("");

                const response =
                    await getMenuItemById(id);

                console.log(
                    "Menu item:",
                    response.data
                );

                setFormData({
                    name: response.data.name || "",
                    description:
                        response.data.description || "",
                    price:
                        response.data.price ?? "",
                    category:
                        response.data.category || "",
                    imageUrl:
                        response.data.imageUrl || "",
                    available:
                        response.data.available ?? true,
                });

            } catch (error) {

                console.error(
                    "Get menu item error:",
                    error
                );

                setError(
                    error.response?.data?.message ||
                    "Failed to load menu item."
                );

            } finally {

                setLoading(false);

            }
        };

        fetchMenuItem();

    }, [id]);


    const handleChange = (e) => {

        const { name, value, type, checked } = e.target;

        setFormData((currentData) => ({
            ...currentData,
            [name]:
                type === "checkbox"
                    ? checked
                    : value,
        }));
    };


    const handleSubmit = async (e) => {

        e.preventDefault();

        try {

            setSaving(true);
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

            const response =
                await updateMenuItem(
                    id,
                    menuData
                );

            console.log(
                "Updated menu item:",
                response.data
            );

            setSuccess(
                "Menu item updated successfully!"
            );

        } catch (error) {

            console.error(
                "Update menu item error:",
                error
            );

            setError(
                error.response?.data?.message ||
                "Failed to update menu item."
            );

        } finally {

            setSaving(false);

        }
    };


    if (loading) {

        return (
            <div className="container mt-5">

                <h4 className="text-center">
                    Loading menu item...
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
                                Edit Menu Item
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
                                    />

                                </div>


                                {/* Available */}

                                <div className="form-check mb-4">

                                    <input
                                        type="checkbox"
                                        name="available"
                                        id="available"
                                        className="form-check-input"
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


                                <button
                                    type="submit"
                                    className="btn btn-danger w-100"
                                    disabled={saving}
                                >
                                    {saving
                                        ? "Saving..."
                                        : "Update Menu Item"}
                                </button>

                            </form>


                            <button
                                type="button"
                                className="btn btn-outline-secondary w-100 mt-3"
                                onClick={() =>
                                    navigate(
                                        `/admin/restaurants/${formData.restaurantId}/menu`
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

export default EditMenuItem;