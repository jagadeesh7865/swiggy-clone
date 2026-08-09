import api from "./api";

export const login = (loginData) => {
  return api.post("/auth/login", loginData);
};

export const register = (registerData) => {
  return api.post("/users/register", registerData);
};