import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

function Navbar() {

    const navigate = useNavigate();

    const { role, logoutUser } = useAuth();

    const token = localStorage.getItem("token");


    const logout = () => {

        logoutUser();

        navigate("/login");
    };


    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-danger">

            <div className="container">

                <Link
                    className="navbar-brand fw-bold"
                    to="/"
                >
                    ByteBite
                </Link>


                <div className="navbar-nav ms-auto">

                    {/* Home */}

                    <Link
                        className="nav-link"
                        to="/"
                    >
                        Home
                    </Link>


                    {/* Not logged in */}

                    {!token ? (

                        <>

                            <Link
                                className="nav-link"
                                to="/login"
                            >
                                Login
                            </Link>


                            <Link
                                className="nav-link"
                                to="/register"
                            >
                                Register
                            </Link>

                        </>

                    ) : (

                        <>

                            {/* CUSTOMER MENU */}

                            {role === "CUSTOMER" && (

                                <>

                                    <Link
                                        className="nav-link"
                                        to="/restaurants"
                                    >
                                        Restaurants
                                    </Link>


                                    <Link
                                        className="nav-link"
                                        to="/cart"
                                    >
                                        Cart 🛒
                                    </Link>


                                    <Link
                                        className="nav-link"
                                        to="/orders"
                                    >
                                        My Orders
                                    </Link>


                                    <Link
                                        className="nav-link"
                                        to="/favorites"
                                    >
                                        ❤️ Favorites
                                    </Link>

                                </>

                            )}


                            {/* ADMIN MENU */}

                            {role === "ADMIN" && (

                                <>

                                    <Link
                                        className="nav-link"
                                        to="/admin/restaurants"
                                    >
                                        Manage Restaurants
                                    </Link>


                                    <Link
                                        className="nav-link"
                                        to="/admin/restaurants/add"
                                    >
                                        Add Restaurant
                                    </Link>

                                </>

                            )}


                            {/* Logout */}

                            <button
                                className="btn btn-light ms-2"
                                onClick={logout}
                            >
                                Logout
                            </button>

                        </>

                    )}

                </div>

            </div>

        </nav>
    );
}

export default Navbar;