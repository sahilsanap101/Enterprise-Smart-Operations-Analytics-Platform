import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./auth/Login";
import ProtectedRoute from "./components/ProtectedRoute";

import AdminDashboard from "./dashboards/AdminDashboard";
import ManagerDashboard from "./dashboards/ManagerDashboard";
import EmployeeDashboard from "./dashboards/EmployeeDashboard";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />

        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/manager"
          element={
            <ProtectedRoute>
              <ManagerDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employee"
          element={
            <ProtectedRoute>
              <EmployeeDashboard />
            </ProtectedRoute>
          }
        />

        <Route path="*" element={<h2>Page Not Found</h2>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
