import React, { useState } from "react";
import "./../components/AuthForm.css";
import { FaEnvelope, FaLock } from "react-icons/fa";
import API from "../utils/api";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import Loader from "../components/Loader";

const Login = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setLoginData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const res = await API.post("/api/public/login", loginData);
      const { token } = res.data;

      localStorage.setItem("token", token);
      toast.success("Login Successful!");
      setTimeout(() => navigate("/home"), 1500);
    } catch (error) {
      const errorMessage =
        error.response?.data?.error || "An unexpected error occurred!";
      toast.error(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {loading && <Loader />}
      <div className="auth-container">
        <form className="auth-card" onSubmit={handleLogin}>
          <h2>Login</h2>
          <div className="input-group">
            <FaEnvelope className="icon" />
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={loginData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="input-group">
            <FaLock className="icon" />
            <input
              type="password"
              name="password"
              placeholder="Enter password"
              value={loginData.password}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className="btn">
            Login
          </button>
          <p className="redirect-text">
            New user? <span onClick={() => navigate("/")}>Register</span>
          </p>
        </form>
      </div>
    </>
  );
};

export default Login;
