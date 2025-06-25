import React, { useEffect, useState } from "react";
import Sidebar from "../components/Sidebar";
import { authFetch, getCurrentUser, logout } from "../api";
import { useNavigate } from "react-router-dom";

const ExpenseManagement = () => {
  const [expenses, setExpenses] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [logoutLoading, setLogoutLoading] = useState(false);
  const [logoutError, setLogoutError] = useState("");
  const user = getCurrentUser();
  const username = user?.sub;
  const navigate = useNavigate();

  useEffect(() => {
    if (!username) {
      setError("User not authenticated");
      setLoading(false);
      return;
    }
    async function fetchExpenses() {
      setLoading(true);
      setError("");
      try {
        const res = await authFetch(`/api/expenses/user/${username}`);
        const data = await res.json();
        if (!res.ok) throw new Error(data.message || "Failed to fetch expenses");
        setExpenses(data.data || []);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
    fetchExpenses();
  }, [username]);

  const handleLogout = async () => {
    setLogoutLoading(true);
    setLogoutError("");
    try {
      await logout();
      navigate("/")
    } catch (err) {
      setLogoutError(err.message);
    } finally {
      setLogoutLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen bg-blue-50">
      <Sidebar onLogout={handleLogout} loading={logoutLoading} />
      <main className="flex-1 p-10">
        <h1 className="text-4xl font-bold text-indigo-500 mb-8">Expense Management</h1>
        {logoutError && <div className="text-red-500 text-sm mb-4">{logoutError}</div>}
        <div className="bg-white rounded-lg shadow p-8 flex flex-col gap-8 min-h-[300px]">
          {loading && <div className="text-lg text-slate-500">Loading expenses...</div>}
          {error && <div className="text-red-500 text-lg">{error}</div>}
          {!loading && !error && expenses && expenses.length === 0 && (
            <div className="text-center text-xl text-slate-700">
              You have no expenses yet.<br />
              <span className="text-sky-400 font-semibold">Start by adding your first expense!</span>
            </div>
          )}
          {/* TODO: Show expenses table/list if expenses exist */}
        </div>
      </main>
    </div>
  );
};

export default ExpenseManagement; 