import { jwtDecode } from "jwt-decode";

export const getUserRole = () => {
  try {
    const token = localStorage.getItem("token");
    if (!token) return null;

    const decoded = jwtDecode(token);

    if (decoded.role) {
      return decoded.role.replace("ROLE_", "");
    }

    if (Array.isArray(decoded.roles) && decoded.roles.length > 0) {
      return decoded.roles[0].replace("ROLE_", "");
    }

    if (Array.isArray(decoded.authorities) && decoded.authorities.length > 0) {
      return decoded.authorities[0].replace("ROLE_", "");
    }

    console.error("JWT has no role info:", decoded);
    return null;
  } catch (e) {
    console.error("JWT decode failed:", e);
    return null;
  }
};