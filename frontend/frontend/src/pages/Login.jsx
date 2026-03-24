import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../api/axios';

const Login = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({ email: '', password: '' });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try 
        {
            const response = await api.post('/login', formData);
            
            // On stocke le token pour l'authentification API
            localStorage.setItem('access_token', response.data.access_token);
            
            // On stocke les infos utilisateur (ID, nom, etc.)
            // C'est ce qui règle le problème du "null" dans localStorage.getItem("user")
            localStorage.setItem('user', JSON.stringify(response.data.user));
            
            navigate('/');
        } 
        
        catch (error) 
        {
            alert(error.response?.data?.message || 'Identifiants incorrects');
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900 flex items-center justify-center p-6 relative overflow-hidden">
            
            {/* Cercles décoratifs d'arrière-plan */}
            <div className="absolute top-[-10%] right-[-10%] w-72 h-72 bg-purple-600 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-blob"></div>
            <div className="absolute bottom-[-10%] left-[-10%] w-72 h-72 bg-violet-600 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-blob animation-delay-2000"></div>

            {/* Carte de connexion Effet Glassmorphism */}
            <div className="max-w-md w-full bg-white/10 backdrop-blur-xl rounded-3xl shadow-2xl border border-white/20 p-10 relative z-10">
                <div className="text-center mb-10">
                    <h2 className="text-3xl font-bold text-white tracking-tight">Connexion</h2>
                    <p className="text-purple-200/70 mt-2 font-light">Ravi de vous revoir sur Connect'In</p>
                </div>
                
                <form onSubmit={handleSubmit} className="space-y-6">
                    <div>
                        <label className="block text-sm font-medium text-purple-100 mb-1.5 ml-1">Email</label>
                        <input 
                            type="email" 
                            required
                            className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl focus:bg-white/10 focus:ring-2 focus:ring-purple-400 focus:border-transparent outline-none transition-all duration-200 text-white placeholder-white/30"
                            placeholder="votre@email.com"
                            onChange={e => setFormData({...formData, email: e.target.value})} 
                        />
                    </div>
                    
                    <div>
                        <label className="block text-sm font-medium text-purple-100 mb-1.5 ml-1">Mot de passe</label>
                        <input 
                            type="password" 
                            required
                            className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl focus:bg-white/10 focus:ring-2 focus:ring-purple-400 focus:border-transparent outline-none transition-all duration-200 text-white placeholder-white/30"
                            placeholder="••••••••"
                            onChange={e => setFormData({...formData, password: e.target.value})} 
                        />
                    </div>

                    <button 
                        type="submit" 
                        className="w-full bg-purple-600 hover:bg-purple-500 text-white font-bold py-3.5 rounded-xl shadow-lg shadow-purple-900/20 transform transition active:scale-[0.97] duration-200 tracking-wide mt-2"
                    >
                        Se connecter
                    </button>
                </form>

                <p className="mt-8 text-center text-purple-200/50 text-sm">
                    Pas encore de compte ? 
                    <Link to="/register" className="text-white font-semibold hover:text-purple-300 ml-1.5 transition-colors underline underline-offset-4 decoration-purple-500/50">
                        Inscrivez-vous
                    </Link>
                </p>
            </div>
        </div>
    );
};

export default Login;