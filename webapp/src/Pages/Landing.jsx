import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";

const testimonials = [
  {
    quote: "BudgetApp made it easy to finally understand where my money goes. I'm saving more every month!",
    author: "Taylor R., Satisfied Customer"
  },
  {
    quote: "I love how simple and intuitive BudgetApp is. My finances have never been more organized!",
    author: "Jordan M., Happy User"
  },
  {
    quote: "Thanks to BudgetApp, I reached my savings goals faster than I thought possible.",
    author: "Morgan S., Financial Planner"
  },
  {
    quote: "The best budgeting tool I've ever used. Highly recommended!",
    author: "Alex P., Loyal Customer"
  },
  {
    quote: "Tracking expenses and planning for the future is a breeze with BudgetApp.",
    author: "Casey L., Delighted Client"
  }
];

const FADE_DURATION = 1500; // ms
const DISPLAY_DURATION = 10000; // ms

const features = [
  {
    title: "Income Management",
    summary: "Easily track all your income sources in one place. Visualize your earnings, categorize deposits, and gain insights to maximize your financial growth.",
    icon: (
      <svg className="w-10 h-10 text-sky-400 mb-4" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" d="M12 8v8m0 0l-3-3m3 3l3-3m6 4V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2h12a2 2 0 002-2z" /></svg>
    )
  },
  {
    title: "Expense Management",
    summary: "Monitor your spending with powerful tools. Categorize expenses, set budgets, and receive alerts to stay on track and avoid overspending.",
    icon: (
      <svg className="w-10 h-10 text-indigo-500 mb-4" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" d="M9 14l6-6m2 2a9 9 0 11-4-7.75" /></svg>
    )
  },
  {
    title: "Savings Plans",
    summary: "Set and achieve your savings goals with ease. Create custom plans, track your progress, and celebrate milestones as you build your future.",
    icon: (
      <svg className="w-10 h-10 text-green-400 mb-4" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" d="M12 8c-2.21 0-4 1.79-4 4s1.79 4 4 4 4-1.79 4-4-1.79-4-4-4zm0 0V4m0 12v4m8-8h-4m-8 0H4" /></svg>
    )
  }
];

function useFadeInOnScroll() {
  const ref = useRef();
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    const node = ref.current;
    if (!node) return;
    const observer = new window.IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          setVisible(true);
          observer.disconnect();
        }
      },
      { threshold: 0.20 }
    );
    observer.observe(node);
    return () => observer.disconnect();
  }, []);

  return [ref, visible];
}

const Landing = () => {
  const [current, setCurrent] = useState(0);
  const [fade, setFade] = useState(true);
  const timeoutRef = useRef();

  // Fade-in hooks for features and testimonials
  const [featuresRef, featuresVisible] = useFadeInOnScroll();
  const [testimonialsRef, testimonialsVisible] = useFadeInOnScroll();
  const [pricingRef, pricingVisible] = useFadeInOnScroll();

  useEffect(() => {
    timeoutRef.current = setTimeout(() => setFade(false), DISPLAY_DURATION - FADE_DURATION);
    const switchTimeout = setTimeout(() => {
      setCurrent((prev) => (prev + 1) % testimonials.length);
      setFade(true);
    }, DISPLAY_DURATION);
    return () => {
      clearTimeout(timeoutRef.current);
      clearTimeout(switchTimeout);
    };
  }, [current]);

  useEffect(() => {
    // Smooth scroll to section if coming from another page
    const section = sessionStorage.getItem('scrollToSection');
    if (section) {
      setTimeout(() => {
        const el = document.getElementById(section);
        if (el) {
          el.scrollIntoView({ behavior: 'smooth' });
        }
        sessionStorage.removeItem('scrollToSection');
      }, 100); // slight delay to ensure DOM is ready
    }
  }, []);

  return (
    <div className="bg-blue-50 min-h-screen flex flex-col">
      {/* Hero Section */}
      <section id="hero" className="max-w-7xl mx-auto w-full flex flex-col md:flex-row items-center gap-12 px-6" style={{ minHeight: '100vh' }}>
        <div className="flex-1 flex flex-col items-start justify-center py-24">
          <h1 className="text-5xl md:text-7xl font-bold text-slate-800 mb-8 leading-tight">Take Control of Your Finances</h1>
          <p className="text-2xl md:text-3xl text-slate-800 mb-12 max-w-2xl">
            BudgetApp helps you track your income, manage expenses, and build savings plans—all in one intuitive platform. Start your journey to financial freedom today.
          </p>
          <div className="flex gap-6 mb-8">
            <Link to="/registration" className="bg-sky-400 hover:bg-sky-500 text-white font-semibold px-7 py-3 rounded-xl text-lg transition">Get Started Free</Link>
            <Link to="/login" className="bg-white border border-sky-400 text-sky-400 hover:bg-sky-50 font-semibold px-7 py-3 rounded-xl text-lg transition">Log In</Link>
          </div>
          <span className="text-slate-500 text-med">No credit card required. Cancel anytime.</span>
        </div>
        <div className="flex-1 flex items-center justify-center py-24">
          {/* Placeholder for hero image/illustration */}
          <div className="w-full h-[28rem] md:h-[36rem] bg-sky-100 rounded-2xl shadow-xl flex items-center justify-center">
            <span className="text-sky-400 text-2xl font-bold">[Budget Dashboard Illustration]</span>
          </div>
        </div>
      </section>
      {/* Features Section */}
      <section
        id="features"
        ref={featuresRef}
        className={`max-w-6xl mx-auto w-full py-12 px-4 transition-opacity duration-1000 ${featuresVisible ? 'opacity-100' : 'opacity-0'}`}
      >
        <h2 className="text-3xl font-bold text-slate-800 text-center mb-10">Powerful Features for Financial Success</h2>
        <div className="flex flex-col md:flex-row gap-8 justify-center">
          {features.map((feature, idx) => (
            <div key={feature.title} className="flex-1 bg-white rounded-xl shadow p-8 flex flex-col items-center text-center border border-slate-100 hover:shadow-lg transition">
              {feature.icon}
              <h3 className="text-xl font-semibold text-slate-800 mb-2">{feature.title}</h3>
              <p className="text-slate-600 text-base">{feature.summary}</p>
            </div>
          ))}
        </div>
      </section>
      {/* Rotating Testimonial Section */}
      <section
        ref={testimonialsRef}
        className={`max-w-3xl mx-auto w-full py-8 px-4 transition-opacity duration-1000 ${testimonialsVisible ? 'opacity-100' : 'opacity-0'}`}
      >
        <blockquote
          className={`text-2xl md:text-3xl text-slate-800 font-light italic text-center mb-4 transition-opacity duration-[1500ms] ${fade ? 'opacity-100' : 'opacity-0'}`}
          style={{ minHeight: 80 }}
        >
          “{testimonials[current].quote}”
        </blockquote>
        <div className={`text-center text-slate-500 text-base transition-opacity duration-[1500ms] ${fade ? 'opacity-100' : 'opacity-0'}`}>
          — {testimonials[current].author}
        </div>
      </section>
      {/* Pricing Section */}
      <section
        id="pricing"
        ref={pricingRef}
        className={`max-w-3xl mx-auto w-full py-16 px-4 transition-opacity duration-1000 ${pricingVisible ? 'opacity-100' : 'opacity-0'}`}
      >
        <h2 className="text-3xl font-bold text-slate-800 text-center mb-8">Simple, One-Time Pricing</h2>
        <div className="bg-white rounded-2xl shadow-xl p-10 flex flex-col items-center border border-slate-100">
          <div className="text-slate-800 text-lg mb-2">Start for free with a 1-week trial</div>
          <div className="flex items-end mb-4">
            <span className="text-5xl font-extrabold text-sky-400 mr-2">$20</span>
            <span className="text-slate-500 text-lg mb-1">one-time fee</span>
          </div>
          <ul className="mb-8 text-slate-700 text-base space-y-2 text-center">
            <li>✔️ Unlimited income & expense tracking</li>
            <li>✔️ Custom savings plans</li>
            <li>✔️ All features unlocked, forever</li>
            <li>✔️ No recurring charges</li>
            <li>✔️ Cancel anytime during trial</li>
          </ul>
          <Link to="/registration" className="bg-sky-400 hover:bg-sky-500 text-white font-semibold px-8 py-4 rounded-xl text-lg transition w-full text-center">Start Free Trial</Link>
          <div className="text-slate-500 text-sm mt-4">No credit card required for trial</div>
        </div>
      </section>
    </div>
  );
};

export default Landing; 