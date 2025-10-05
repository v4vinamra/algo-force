// utils/auth.js

export const getToken = () => {
  return localStorage.getItem("token");
};

export const getTokenExpiration = (token) => {
  if (!token) return null;

  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const payload = JSON.parse(window.atob(base64));
    return payload.exp * 1000; // convert to ms
  } catch (err) {
    return null;
  }
};

export const isLoggedIn = () => {
  const token = getToken();
  const exp = getTokenExpiration(token);
  if (!token || !exp) return false;
  return Date.now() < exp;
};

export const logout = () => {
  localStorage.removeItem("token");
};
