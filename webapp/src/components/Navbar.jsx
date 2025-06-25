import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="bg-white shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16 items-center">
          <div className="flex-shrink-0 text-2xl font-bold text-sky-400">BudgetingApp</div>
          <div className="flex space-x-6">
            <Link to="/login" className="text-slate-800 hover:text-sky-400 transition font-medium">Login</Link>
            <Link to="/registration" className="text-slate-800 hover:text-sky-400 transition font-medium">Register</Link>
            <Link to="/pricing" className="text-slate-800 hover:text-sky-400 transition font-medium">Pricing</Link>
            <Link to="/contact" className="text-indigo-500 hover:text-sky-400 transition font-medium">Contact Us</Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar; 