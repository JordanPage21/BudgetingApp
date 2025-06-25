import React from "react";
import { useNavigate } from "react-router-dom";

const Sidebar = ({ onLogout, loading }) => {
  const navigate = useNavigate();
  return (
    <aside className="w-64 min-h-screen bg-indigo-500 flex flex-col py-8 px-4">
      <div>
        <div className="flex items-center mb-10">
          <div className="w-8 h-8 bg-sky-400 rounded-lg flex items-center justify-center mr-2">
            {/* Placeholder for logo */}
            <span className="text-white text-2xl font-bold">$</span>
          </div>
          <span className="text-white text-xl font-semibold">BudgetApp</span>
        </div>
        <nav className="flex flex-col gap-2">
          <button className="w-full text-left px-4 py-2 rounded-lg bg-indigo-400 text-white font-medium">Dashboard</button>
          <button className="w-full text-left px-4 py-2 rounded-lg text-indigo-100 hover:bg-indigo-400 hover:text-white font-medium transition">Budgets</button>
        </nav>
      </div>
      <div className="flex-1" />
      <button
        className="w-full mt-8 py-2 rounded-lg bg-sky-400 text-white font-semibold hover:bg-sky-500 transition"
        onClick={onLogout}
        disabled={loading}
      >
        {loading ? 'Logging out...' : 'Logout'}
      </button>
    </aside>
  );
};

export default Sidebar; 