import { Navigate } from "react-router-dom";
import { isAuthenticated } from "../auth/AuthService";

export default function ProtectedRoute({ children }) {
  return isAuthenticated() ? children : <Navigate to="/login" replace />;
}
