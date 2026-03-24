import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api", // URL du Sail
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
