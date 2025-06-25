import React, { useState } from "react";
import Sidebar from "../components/Sidebar";
import { logout } from "../api";
import { useNavigate } from "react-router-dom";

const BudgetManagement = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogout = async () => {
    setLoading(true);
    setError("");
    try {
      await logout();
      navigate("/")

    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen bg-blue-50">
      <Sidebar onLogout={handleLogout} loading={loading} />
      <main className="flex-1 p-10">
        <h1 className="text-4xl font-bold text-indigo-500 mb-8">Budget Management</h1>
        {error && <div className="text-red-500 text-sm mb-4">{error}</div>}
        <div className="bg-white rounded-lg shadow p-8 flex flex-col gap-8 items-center justify-center text-3xl text-slate-800 min-h-[300px]">
          Budget Management Page Coming Soon
        </div>
      </main>
    </div>
  );
};

export default BudgetManagement; 