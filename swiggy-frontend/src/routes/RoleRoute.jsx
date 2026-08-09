import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function RoleRoute({ allowedRole, children }) {

    const { role } = useAuth();

    if (!role) {

        return (
            <Navigate
                to="/login"
                replace
            />
        );
    }

    if (role !== allowedRole) {

        return (
            <Navigate
                to="/"
                replace
            />
        );
    }

    return children;
}

export default RoleRoute;