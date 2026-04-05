import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from '../api/axios';

const Register = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        firstName: '', lastName: '', email: '', password: '', password_confirmation: ''
    });

    // État pour stocker les erreurs de validation du Backend
    const [errors, setErrors] = useState({});

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({}); 

        try {
            await api.post('/register', formData);
            navigate('/');
        } catch (error) {
            // Log pour déboguer si ça ne marche toujours pas
            console.log("Status d'erreur:", error.response?.status);
            console.log("Données d'erreur:", error.response?.data);

            // Si c'est une erreur de validation (422)
            if (error.response && error.response.status === 422) {
                setErrors(error.response.data.errors);
            } else {
                // Si c'est autre chose (ex: serveur éteint), on garde l'alerte
                alert(error.response?.data?.message || 'Erreur lors de l\'inscription');
            }
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900 flex items-center justify-center p-6 relative overflow-hidden">
            
            <div className="absolute top-[-10%] left-[-10%] w-72 h-72 bg-purple-600 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-blob"></div>
            <div className="absolute bottom-[-10%] right-[-10%] w-72 h-72 bg-violet-600 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-blob animation-delay-2000"></div>

            <div className="max-w-lg w-full bg-white/10 backdrop-blur-xl rounded-3xl shadow-2xl border border-white/20 p-10 relative z-10">
                <div className="text-center mb-10">
                    <h2 className="text-3xl font-bold text-white tracking-tight">Rejoindre Connect'In</h2>
                    <p className="text-purple-200/70 mt-2 font-light">Créez votre compte en quelques secondes</p>
                </div>
                
                <form onSubmit={handleSubmit} className="space-y-5">
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <label className="block text-sm font-medium text-purple-100 mb-1.5 ml-1">Prénom</label>
                            <input 
                                type="text" required
                                className="w-full px-4 py-2.5 bg-white/5 border border-white/10 rounded-xl focus:bg-white/10 focus:ring-2 focus:ring-purple-400 focus:border-transparent outline-none transition-all duration-200 text-white"
                                onChange={e => setFormData({...formData, firstName: e.target.value})} 
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-purple-100 mb-1.5 ml-1">Nom</label>
                            <input 
                                type="text" required
                                className="w-full px-4 py-2.5 bg-white/5 border border-white/10 rounded-xl focus:bg-white/10 focus:ring-2 focus:ring-purple-400 focus:border-transparent outline-none transition-all duration-200 text-white"
                                onChange={e => setFormData({...formData, lastName: e.target.value})} 
                            />
                        </div>
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-purple-100 mb-1.5 ml-1">Email professionnel</label>
                        <input 
                            type="email" required
                            className={`w-full px-4 py-2.5 bg-white/5 border ${errors.email ? 'border-red-400' : 'border-white/10'} rounded-xl focus:bg-white/10 focus:ring-2 focus:ring-purple-400 focus:border-transparent outline-none transition-all duration-200 text-white placeholder-white/30`}
                            placeholder="prenom.nom@epitech.eu"
                            onChange={e => setFormData({...formData, email: e.target.value})} 
                        />
                        {/* Affichage du message d'erreur spécifique à l'email (Regex ou Unique) */}
                        {errors.email && (
                            <p className="text-red-400 text-xs mt-1.5 ml-1 animate-pulse font-medium">
                                {errors.email[0]}
                            </p>
                        )}
                    </div>

                    <div className="space-y-4">
                        <div>
                            <label className="block text-sm font-medium text-purple-100 mb-1.5 ml-1">Mot de passe</label>
                            <input 
                                type="password" required
                                className="w-full px-4 py-2.5 bg-white/5 border border-white/10 rounded-xl focus:bg-white/10 focus:ring-2 focus:ring-purple-400 focus:border-transparent outline-none transition-all duration-200 text-white"
                                onChange={e => setFormData({...formData, password: e.target.value})} 
                            />
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-purple-100 mb-1.5 ml-1">Confirmation</label>
                            <input 
                                type="password" required
                                className={`w-full px-4 py-2.5 bg-white/5 border ${errors.password ? 'border-red-400' : 'border-white/10'} rounded-xl focus:bg-white/10 focus:ring-2 focus:ring-purple-400 focus:border-transparent outline-none transition-all duration-200 text-white`}
                                onChange={e => setFormData({...formData, password_confirmation: e.target.value})} 
                            />
                            {errors.password && (
                                <p className="text-red-400 text-xs mt-1.5 ml-1 font-medium">
                                    Les mots de passe ne correspondent pas.
                                </p>
                            )}
                        </div>
                    </div>

                    <button 
                        type="submit" 
                        className="w-full mt-6 bg-purple-600 hover:bg-purple-500 text-white font-bold py-3.5 rounded-xl shadow-lg shadow-purple-900/20 transform transition active:scale-[0.97] duration-200 tracking-wide"
                    >
                        Créer mon compte
                    </button>
                </form>

                <p className="mt-8 text-center text-purple-200/50 text-sm">
                    Déjà membre ? 
                    <Link to="/login" className="text-white font-semibold hover:text-purple-300 ml-1.5 transition-colors underline underline-offset-4 decoration-purple-500/50">
                        Se connecter
                    </Link>
                </p>
            </div>
        </div>
    );
};

export default Register;