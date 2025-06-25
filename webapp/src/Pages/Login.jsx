import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../api";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      await login({ username, password });
      setSuccess(true);
      setTimeout(() => navigate("/dashboard"), 2000);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-blue-50">
      <div className="bg-white rounded-lg shadow-lg w-full max-w-md">
        {/* Top image section */}
        <div className="h-32 rounded-t-lg bg-cover bg-center flex items-center justify-center" style={{ backgroundImage: 'url(https://images.unsplash.com/photo-1519389950473-47ba0277781c?auto=format&fit=crop&w=600&q=80)' }}>
          <h2 className="text-2xl font-bold text-white tracking-wide drop-shadow text-center w-full">SIGN IN</h2>
        </div>
        {/* Form section */}
        <form className="px-8 py-8" onSubmit={handleSubmit}>
          <div className="mb-6">
            <label className="block text-slate-800 text-sm font-medium mb-1" htmlFor="username">Username</label>
            <input id="username" type="text" placeholder="Enter username" className="w-full border-b border-slate-200 focus:outline-none focus:border-sky-400 bg-transparent text-slate-800 placeholder-slate-400 py-2" value={username} onChange={e => setUsername(e.target.value)} required disabled={loading || success} />
          </div>
          <div className="mb-4">
            <label className="block text-slate-800 text-sm font-medium mb-1" htmlFor="password">Password</label>
            <input id="password" type="password" placeholder="Enter password" className="w-full border-b border-slate-200 focus:outline-none focus:border-sky-400 bg-transparent text-slate-800 placeholder-slate-400 py-2" value={password} onChange={e => setPassword(e.target.value)} required disabled={loading || success} />
          </div>
          <div className="flex items-center justify-between mb-6">
            <a href="#" className="text-sm text-indigo-500 hover:text-sky-400">Forgot Password?</a>
          </div>
          {error && <div className="text-red-500 text-sm mb-4 text-center">{error}</div>}
          {success && <div className="text-green-600 text-sm mb-4 text-center font-semibold">Login Successful!</div>}
          <button type="submit" className="w-full bg-sky-400 text-white py-2 rounded-full font-semibold hover:bg-sky-500 transition mb-4" disabled={loading || success}>{loading ? "Logging in..." : "Login"}</button>
          <div className="text-center text-sm text-slate-800">
            Don't have an account?{' '}
            <Link to="/registration" className="text-indigo-500 hover:text-sky-400 font-medium">Sign up</Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login; 