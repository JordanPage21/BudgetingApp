import React from "react";
import { useLocation, useNavigate } from "react-router-dom";

const scrollToSection = (id) => {
  const el = document.getElementById(id);
  if (el) {
    el.scrollIntoView({ behavior: 'smooth' });
  }
};

const Footer = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleNav = (e, id) => {
    e.preventDefault();
    if (location.pathname === "/") {
      scrollToSection(id);
    } else {
      sessionStorage.setItem('scrollToSection', id);
      navigate("/");
    }
  };

  return (
    <footer className="bg-white border-t border-slate-100 pt-8 pb-6">
      <div className="max-w-7xl mx-auto px-4 flex flex-col items-center">
        <nav className="mb-4">
          <ul className="flex flex-wrap justify-center gap-8 text-base">
            <li><a href="/#hero" className="text-indigo-500 hover:text-sky-400 transition" onClick={e => handleNav(e, 'hero')}>Home</a></li>
            <li><a href="/#features" className="text-indigo-500 hover:text-sky-400 transition" onClick={e => handleNav(e, 'features')}>Features</a></li>
            <li><a href="/#pricing" className="text-indigo-500 hover:text-sky-400 transition" onClick={e => handleNav(e, 'pricing')}>Pricing</a></li>
            <li><a href="#" className="text-indigo-500 hover:text-sky-400 transition">FAQs</a></li>
            <li><a href="#" className="text-indigo-500 hover:text-sky-400 transition">About</a></li>
          </ul>
        </nav>
        <hr className="w-full border-slate-200 mb-4" />
        <span className="text-slate-800 text-base">&copy; {new Date().getFullYear()} BudgetApp, Inc</span>
      </div>
    </footer>
  );
};

export default Footer; 