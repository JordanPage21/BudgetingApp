import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./Pages/Login";
import Registration from "./Pages/Registration";
import Dashboard from "./Pages/Dashboard";
import Footer from "./components/Footer";
import Landing from "./Pages/Landing";
import { getJwtToken } from "./api";

// Placeholder components for other pages
const Pricing = () => <div className="min-h-screen flex items-center justify-center text-2xl text-slate-800">Pricing Page Coming Soon</div>;
const Contact = () => <div className="min-h-screen flex items-center justify-center text-2xl text-slate-800">Contact Us Page Coming Soon</div>;

function PrivateRoute({ children }) {
  const isAuthenticated = !!getJwtToken();
  return isAuthenticated ? children : <Navigate to="/login" replace />;
}

function App() {
  return (
    <Router>
      <div className="flex flex-col min-h-screen">
        <div className="flex-1">
          <Routes>
            <Route path="/" element={<Landing />} />
            <Route path="/login" element={<Login />} />
            <Route path="/registration" element={<Registration />} />
            <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
            <Route path="/pricing" element={<Pricing />} />
            <Route path="/contact" element={<Contact />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
