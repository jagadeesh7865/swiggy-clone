import { useState } from "react";
import { login } from "../../services/authServices";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const { loginUser } = useAuth();


    const handleLogin = async (e) => {

        e.preventDefault();

        try {

            const response = await login({
                email,
                password,
            });

            console.log("Login response:", response.data);

            // Store token and role in AuthContext
            loginUser(response.data.token);

            alert("Login Successful");

            navigate("/");

        } catch (error) {

            console.error("Login error:", error);

            alert(
                error.response?.data?.message ||
                "Invalid Credentials"
            );
        }
    };


    return (

        <div className="container mt-5">

            <div className="row justify-content-center">

                <div className="col-md-5">

                    <div className="card shadow">

                        <div className="card-body">

                            <h3 className="text-center mb-4">
                                Login
                            </h3>


                            <form onSubmit={handleLogin}>

                                {/* Email */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Email
                                    </label>

                                    <input
                                        type="email"
                                        className="form-control"
                                        value={email}
                                        onChange={(e) =>
                                            setEmail(e.target.value)
                                        }
                                        required
                                    />

                                </div>


                                {/* Password */}

                                <div className="mb-3">

                                    <label className="form-label">
                                        Password
                                    </label>

                                    <input
                                        type="password"
                                        className="form-control"
                                        value={password}
                                        onChange={(e) =>
                                            setPassword(e.target.value)
                                        }
                                        required
                                    />

                                </div>


                                {/* Login button */}

                                <button
                                    type="submit"
                                    className="btn btn-danger w-100"
                                >
                                    Login
                                </button>

                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default Login;