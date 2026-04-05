import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

const UserMenu = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await api.get("/user");
        setUser(res.data);
      } catch (err) {
        console.error("Erreur menu utilisateur:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchUser();
  }, []);

  const getInitials = () => {
    if (loading) return "...";
    if (!user) return "?";
    return `${user?.firstName?.charAt(0) || ""}${user?.lastName?.charAt(0) || ""}`.toUpperCase();
  };

  return (
    <div className="flex items-center gap-4 bg-white/5 p-2 rounded-2xl border border-white/10 hover:bg-white/10 transition-all shadow-lg">
      <div className="hidden md:block text-right ml-2">
        <p className="text-white text-sm font-bold leading-none">
          {user ? `${user.firstName} ${user.lastName}` : "Invité"}
        </p>
        <p className="text-purple-300/60 text-[10px] uppercase tracking-widest mt-1">
          {user?.role === "admin" ? "Administrateur" : "Membre"}
        </p>
      </div>

      <button
        onClick={() => navigate("/profile")}
        className="relative group focus:outline-none"
        title="Voir mon profil"
      >
        <div className="absolute -inset-0.5 bg-gradient-to-r from-purple-500 to-pink-500 rounded-full blur opacity-30 group-hover:opacity-100 transition duration-300"></div>

        {user?.profil_pic ? (
          <img
            src={
              user.profil_pic.startsWith("http")
                ? user.profil_pic
                : `http://localhost:8080${user.profil_pic}`
            }
            alt="Profil"
            className="relative w-10 h-10 rounded-full border-2 border-white/20 object-cover bg-slate-800"
          />
        ) : (
          <div className="relative w-10 h-10 rounded-full bg-slate-800 flex items-center justify-center text-white text-xs font-bold border-2 border-white/20">
            {getInitials()}
          </div>
        )}
      </button>
    </div>
  );
};

export default UserMenu;
