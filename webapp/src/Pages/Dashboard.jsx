import React, { useState } from "react";
import Sidebar from "../components/Sidebar";
import { logout } from "../api";
import { useNavigate } from "react-router-dom";

const Dashboard = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogout = async () => {
    setLoading(true);
    setError("");
    try {
      await logout();
      navigate("/login");
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
        <h1 className="text-4xl font-bold text-indigo-500 mb-8">Budget Tracking Dashboard</h1>
        {error && <div className="text-red-500 text-sm mb-4">{error}</div>}
        <div className="bg-white rounded-lg shadow p-8 flex flex-col gap-8">
          <div className="flex gap-12 mb-8">
            <div>
              <div className="text-slate-800 text-lg font-semibold">Total Expenses</div>
              <div className="text-3xl font-bold text-slate-800 mt-1">25,460</div>
            </div>
            <div>
              <div className="text-slate-800 text-lg font-semibold">Monthly Savings</div>
              <div className="text-3xl font-bold text-slate-800 mt-1">1,570</div>
            </div>
            <div>
              <div className="text-slate-800 text-lg font-semibold">Annual Income</div>
              <div className="text-3xl font-bold text-slate-800 mt-1">45,390</div>
            </div>
          </div>
          <div className="h-64 flex items-center justify-center text-slate-400 text-xl">
            [Graph Placeholder]
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard; 