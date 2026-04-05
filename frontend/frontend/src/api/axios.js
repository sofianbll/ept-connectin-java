import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_URL;
const api = axios.create({
  baseURL: `${API_BASE_URL}/api`, // URL du Sail
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

// Ajout automatique du token dans les headers s'il existe
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("access_token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
