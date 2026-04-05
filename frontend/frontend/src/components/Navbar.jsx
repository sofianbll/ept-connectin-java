import React from "react";
import { Link, useLocation } from "react-router-dom"; // Import pour la navigation
import LogoutButton from "./LogoutButton";
import UserMenu from "./UserMenu";

const Navbar = () => {
  const location = useLocation();

  // Fonction pour vérifier si un lien est actif (pour le style)
  const isActive = (path) => location.pathname === path;

  return (
    <nav className="sticky top-0 z-50 w-full border-b border-white/10 bg-slate-900/60 backdrop-blur-md px-6 py-3">
      <div className="max-w-6xl mx-auto flex justify-between items-center">
        
        {/* Logo + Navigation principale */}
        <div className="flex items-center gap-8">
          {/* Logo */}
          <Link to="/" className="flex items-center gap-2 group cursor-pointer">
            <div className="w-8 h-8 bg-gradient-to-br from-purple-600 to-pink-500 rounded-lg shadow-lg shadow-purple-500/20 flex items-center justify-center">
              <span className="text-white font-black text-xl italic">C</span>
            </div>
            <h1 className="text-xl font-extrabold tracking-tighter bg-clip-text text-transparent bg-gradient-to-r from-white to-gray-400 group-hover:to-purple-400 transition-all">
              Connect'In
            </h1>
          </Link>

          {/* Liens de Navigation */}
          <div className="hidden md:flex items-center gap-2">
            <Link 
              to="/" 
              className={`px-4 py-2 rounded-xl text-sm font-bold transition-all ${
                isActive('/') 
                ? 'bg-white/10 text-white shadow-inner' 
                : 'text-gray-400 hover:text-white hover:bg-white/5'
              }`}
            >
              🏠 Fil d'actualité
            </Link>
            
            <Link 
              to="/teams" 
              className={`px-4 py-2 rounded-xl text-sm font-bold transition-all ${
                isActive('/teams') 
                ? 'bg-purple-500/20 text-purple-400 border border-purple-500/30' 
                : 'text-gray-400 hover:text-white hover:bg-white/5'
              }`}
            >
              🏢 Mes Teams
            </Link>
          </div>
        </div>

        {/* Actions : UserMenu + Logout */}
        <div className="flex items-center gap-6">
          <UserMenu />
          {/* Séparateur visuel */}
          <div className="h-8 w-[1px] bg-white/10 hidden sm:block"></div>
          <LogoutButton />
        </div>
      </div>
    </nav>
  );
};

export default Navbar;