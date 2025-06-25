import React from "react";

const Footer = () => (
  <footer className="bg-white border-t border-slate-100 pt-8 pb-6">
    <div className="max-w-7xl mx-auto px-4 flex flex-col items-center">
      <nav className="mb-4">
        <ul className="flex flex-wrap justify-center gap-8 text-base">
          <li><a href="/" className="text-indigo-500 hover:text-sky-400 transition">Home</a></li>
          <li><a href="#" className="text-indigo-500 hover:text-sky-400 transition">Features</a></li>
          <li><a href="#" className="text-indigo-500 hover:text-sky-400 transition">Pricing</a></li>
          <li><a href="#" className="text-indigo-500 hover:text-sky-400 transition">FAQs</a></li>
          <li><a href="#" className="text-indigo-500 hover:text-sky-400 transition">About</a></li>
        </ul>
      </nav>
      <hr className="w-full border-slate-200 mb-4" />
      <span className="text-slate-800 text-base">&copy; {new Date().getFullYear()} BudgetApp, Inc</span>
    </div>
  </footer>
);

export default Footer; 