import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { register } from "../../services/authServices";


function Register() {

    const navigate = useNavigate();


    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        mobile: "",
        password: "",
    });


    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");


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

            setLoading(true);
            setError("");
            setSuccess("");


            const response = await register(formData);


            console.log(
                "Registration response:",
                response.data
            );


            setSuccess(
                "Registration successful! Redirecting to login..."
            );


            setTimeout(() => {

                navigate("/login");

            }, 1500);


        } catch (error) {

            console.error(
                "Registration error:",
                error
            );


            setError(
                error.response?.data?.message ||
                "Registration failed. Please try again."
            );


        } finally {

            setLoading(false);

        }
    };


    return (

        <div className="container mt-5 mb-5">

            <div className="row justify-content-center">

                <div className="col-md-6">

                    <div className="card shadow">

                        <div className="card-body">

                            <h2 className="text-center mb-4">
                                Create Account
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

                                {/* First Name */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        First Name
                                    </label>

                                    <input
                                        type="text"
                                        name="firstName"
                                        className="form-control"
                                        value={
                                            formData.firstName
                                        }
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                {/* Last Name */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Last Name
                                    </label>

                                    <input
                                        type="text"
                                        name="lastName"
                                        className="form-control"
                                        value={
                                            formData.lastName
                                        }
                                        onChange={handleChange}
                                        required
                                    />

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
                                        value={
                                            formData.email
                                        }
                                        onChange={handleChange}
                                        required
                                    />

                                </div>


                                {/* Mobile */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Mobile Number
                                    </label>

                                    <input
                                        type="tel"
                                        name="mobile"
                                        className="form-control"
                                        value={
                                            formData.mobile
                                        }
                                        onChange={handleChange}
                                        minLength="10"
                                        maxLength="10"
                                        pattern="[0-9]{10}"
                                        placeholder="10 digit mobile number"
                                        required
                                    />

                                </div>


                                {/* Password */}

                                <div className="mb-4">

                                    <label className="form-label">
                                        Password
                                    </label>

                                    <input
                                        type="password"
                                        name="password"
                                        className="form-control"
                                        value={
                                            formData.password
                                        }
                                        onChange={handleChange}
                                        minLength="6"
                                        required
                                    />

                                    <small className="text-muted">
                                        Password must be at least 6
                                        characters.
                                    </small>

                                </div>


                                {/* Register */}

                                <button
                                    type="submit"
                                    className="btn btn-danger w-100"
                                    disabled={loading}
                                >

                                    {loading
                                        ? "Creating Account..."
                                        : "Register"}

                                </button>

                            </form>


                            <div className="text-center mt-3">

                                <span>
                                    Already have an account?
                                </span>


                                <button
                                    type="button"
                                    className="btn btn-link"
                                    onClick={() =>
                                        navigate("/login")
                                    }
                                >
                                    Login
                                </button>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}


export default Register;