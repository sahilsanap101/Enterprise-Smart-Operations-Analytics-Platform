import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./auth/Login";
import AdminDashboard from "./dashboards/AdminDashboard";
import ManagerDashboard from "./dashboards/ManagerDashboard";
import EmployeeDashboard from "./dashboards/EmployeeDashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import { getUserRole } from "./auth/jwtUtils";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />

        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              {getUserRole() === "ADMIN" ? <AdminDashboard /> : <Navigate to="/login" />}
            </ProtectedRoute>
          }
        />

        <Route
          path="/manager"
          element={
            <ProtectedRoute>
              {getUserRole() === "MANAGER" ? <ManagerDashboard /> : <Navigate to="/login" />}
            </ProtectedRoute>
          }
        />

        <Route
          path="/employee"
          element={
            <ProtectedRoute>
              {getUserRole() === "EMPLOYEE" ? <EmployeeDashboard /> : <Navigate to="/login" />}
            </ProtectedRoute>
          }
        />

        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>
  );
}