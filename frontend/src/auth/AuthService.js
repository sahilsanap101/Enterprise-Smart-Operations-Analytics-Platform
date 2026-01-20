import api from "../api/axios";

export const login = async (email, password) => {
  try {
    const response = await api.post("/auth/login", {
      email,
      password,
    });

    // backend returns plain JWT string
    localStorage.setItem("token", response.data);
    return response.data;
  } catch (err) {
    throw err;
  }
};

export const logout = () => {
  localStorage.removeItem("token");
};

export const isAuthenticated = () => {
  return !!localStorage.getItem("token");
};
