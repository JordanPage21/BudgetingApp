const API_BASE = '/api/auth';

// JWT token utilities
export function setJwtToken(token) {
  localStorage.setItem('jwtToken', token);
}
export function getJwtToken() {
  return localStorage.getItem('jwtToken');
}
export function removeJwtToken() {
  localStorage.removeItem('jwtToken');
}

// Decode JWT payload (without validation)
function decodeJwt(token) {
  try {
    const payload = token.split('.')[1];
    const decoded = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
    return decoded;
  } catch {
    return null;
  }
}

// Get current user info from JWT
export function getCurrentUser() {
  const token = getJwtToken();
  if (!token) return null;
  return decodeJwt(token);
}

// Custom fetch with JWT Authorization header
export async function authFetch(url, options = {}) {
  const token = getJwtToken();
  const headers = { ...(options.headers || {}) };
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  return fetch(url, { ...options, headers });
}

export async function register({ username, password, email }) {
  const res = await authFetch(`${API_BASE}/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password, email })
  });
  if (!res.ok) throw new Error((await res.json()).message || 'Registration failed');
  const data = await res.json();
  console.log(data);
  // Store JWT if present in response
  if (data.data && data.data.accessToken) {
    setJwtToken(data.data.accessToken);
  }
  return data;
}

export async function login({ username, password }) {
  const res = await authFetch(`${API_BASE}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  });
  if (!res.ok) throw new Error((await res.json()).message || 'Login failed');
  const data = await res.json();
  console.log(data);
  // Store JWT if present in response
  if (data.data && data.data.accessToken) {
    setJwtToken(data.data.accessToken);
  }
  return data;
}

export async function logout() {
  const res = await authFetch(`${API_BASE}/logout`, {
    method: 'POST',
    credentials: 'include'
  });
  removeJwtToken();
  if (!res.ok) throw new Error((await res.json()).message || 'Logout failed');
  return res.json();
} 