import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "./AuthService";
import { getUserRole } from "./jwtUtils";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await login(email.trim(), password.trim());

      const role = getUserRole();

      if (role === "ADMIN") navigate("/admin");
      else if (role === "MANAGER") navigate("/manager");
      else if (role === "EMPLOYEE") navigate("/employee");
      else alert("Unknown role");

    } catch (err) {
      alert(
        err?.response?.data?.error || "Invalid credentials"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Login</h2>

      <input
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
      />

      <button type="submit" disabled={loading}>
        {loading ? "Logging in..." : "Login"}
      </button>
    </form>
  );
}
