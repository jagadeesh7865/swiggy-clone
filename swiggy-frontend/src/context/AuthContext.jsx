import { createContext, useContext, useState } from "react";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {

    const [role, setRole] = useState(() => {

        const token = localStorage.getItem("token");

        if (!token) {
            return null;
        }

        try {

            const payload =
                JSON.parse(
                    atob(token.split(".")[1])
                );

            return payload.role || null;

        } catch (error) {

            console.error(
                "Failed to read JWT:",
                error
            );

            return null;
        }
    });


    const loginUser = (token) => {

        localStorage.setItem("token", token);

        try {

            const payload =
                JSON.parse(
                    atob(token.split(".")[1])
                );

            setRole(payload.role || null);

        } catch (error) {

            console.error(
                "Failed to read JWT:",
                error
            );

            setRole(null);
        }
    };


    const logoutUser = () => {

        localStorage.removeItem("token");

        setRole(null);
    };


    return (
        <AuthContext.Provider
            value={{
                role,
                loginUser,
                logoutUser,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}


export function useAuth() {

    return useContext(AuthContext);
}