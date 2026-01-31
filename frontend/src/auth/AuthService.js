import api from "../api/axios";

// LOGIN
export const login = async (email, password) => {
  // 🔥 clear any old/bad token before login
  localStorage.removeItem("token");

  const response = await api.post("/auth/login", {
    email,
    password,
  });

  const token =
    typeof response.data === "string"
      ? response.data
      : response.data.token;

  if (!token) {
    throw new Error("Token not received from backend");
  }

  localStorage.setItem("token", token);
  return token;
};

// LOGOUT
export const logout = () => {
  localStorage.removeItem("token");
};

export const isAuthenticated = () => {
  return !!localStorage.getItem("token");
};