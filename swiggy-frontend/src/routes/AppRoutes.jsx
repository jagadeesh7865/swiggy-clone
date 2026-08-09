import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "../components/layout/Navbar";

import Home from "../pages/home/Home";
import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";

import Restaurants from "../pages/restaurant/Restaurants";
import RestaurantDetails from "../pages/restaurant/RestaurantDetails";
import MenuItems from "../pages/restaurant/MenuItems";

import Cart from "../pages/cart/Cart";
import Checkout from "../pages/checkout/Checkout";

import MyOrders from "../pages/orders/MyOrders";
import OrderDetails from "../pages/orders/OrderDetails";

import MyFavorites from "../pages/favorites/MyFavorites";

import AddRestaurant from "../pages/admin/AddRestaurant";
import AdminRestaurants from "../pages/admin/AdminRestaurants";

import ProtectedRoute from "./ProtectedRoute";
import RoleRoute from "./RoleRoute";
import EditRestaurant from "../pages/admin/EditRestaurant";
import AdminMenu from "../pages/admin/AdminMenu";
import AddMenuItem from "../pages/admin/AddMenuItem";
import EditMenuItem from "../pages/admin/EditMenuItem";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        {/* =========================
                    PUBLIC ROUTES
                ========================= */}

        <Route path="/" element={<Home />} />

        <Route path="/login" element={<Login />} />

        <Route path="/register" element={<Register />} />

        {/* =========================
                    RESTAURANT ROUTES
                ========================= */}

        <Route path="/restaurants" element={<Restaurants />} />

        <Route path="/restaurants/:id" element={<RestaurantDetails />} />

        <Route
          path="/restaurants/:id/menu"
          element={
            <RoleRoute allowedRole="CUSTOMER">
              <MenuItems />
            </RoleRoute>
          }
        />

        {/* =========================
                    CUSTOMER ROUTES
                ========================= */}

        <Route
          path="/cart"
          element={
            <RoleRoute allowedRole="CUSTOMER">
              <Cart />
            </RoleRoute>
          }
        />

        <Route
          path="/checkout"
          element={
            <RoleRoute allowedRole="CUSTOMER">
              <Checkout />
            </RoleRoute>
          }
        />

        <Route
          path="/orders"
          element={
            <RoleRoute allowedRole="CUSTOMER">
              <MyOrders />
            </RoleRoute>
          }
        />

        <Route
          path="/orders/:id"
          element={
            <RoleRoute allowedRole="CUSTOMER">
              <OrderDetails />
            </RoleRoute>
          }
        />

        <Route
          path="/favorites"
          element={
            <RoleRoute allowedRole="CUSTOMER">
              <MyFavorites />
            </RoleRoute>
          }
        />

        {/* =========================
                    ADMIN ROUTES
                ========================= */}

        <Route
          path="/admin/restaurants"
          element={
            <RoleRoute allowedRole="ADMIN">
              <AdminRestaurants />
            </RoleRoute>
          }
        />

        <Route
          path="/admin/restaurants/:id/menu/add"
          element={
            <RoleRoute allowedRole="ADMIN">
              <AddMenuItem />
            </RoleRoute>
          }
        />
        <Route
          path="/admin/restaurants/:id/menu"
          element={
            <RoleRoute allowedRole="ADMIN">
              <AdminMenu />
            </RoleRoute>
          }
        />

        <Route
          path="/admin/restaurants/add"
          element={
            <RoleRoute allowedRole="ADMIN">
              <AddRestaurant />
            </RoleRoute>
          }
        />

        <Route
          path="/admin/restaurants/edit/:id"
          element={
            <RoleRoute allowedRole="ADMIN">
              <EditRestaurant />
            </RoleRoute>
          }
        />
        <Route
          path="/admin/menu/edit/:id"
          element={
            <RoleRoute allowedRole="ADMIN">
              <EditMenuItem />
            </RoleRoute>
          }
        />

        {/* =========================
                    FALLBACK
                ========================= */}

        <Route path="*" element={<Home />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
