import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';

const Profile = () => {
    const navigate = useNavigate(); 
    const [user, setUser] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [deleteContent, setDeleteContent] = useState(false);
    const [message, setMessage] = useState({ type: '', text: '' });
    
    const [formData, setFormData] = useState({
        firstName: '', 
        lastName: '', 
        email: '', 
        current_password: '', 
        new_password: '', 
        new_password_confirmation: ''
    });

    useEffect(() => {
        fetchUser();
    }, []);

    const fetchUser = async () => {
        try {
            const res = await api.get('/user');
            setUser(res.data);
            setFormData({
                ...formData,
                firstName: res.data.firstName || '',
                lastName: res.data.lastName || '',
                email: res.data.email || '',
                current_password: '',
                new_password: '',
                new_password_confirmation: ''
            });
        } catch (err) {
            console.error("Erreur lors de la récupération du profil", err);
        }
    };

    const getInitials = () => {
        return `${user?.firstName?.charAt(0)}${user?.lastName?.charAt(0)}`.toUpperCase();
    };

    const handleAvatarChange = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        const data = new FormData();
        data.append('avatar', file);

        try {
            setMessage({ type: '', text: '' });
            const res = await api.post('/user/avatar', data, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });
            
            setUser({ ...user, profil_pic: res.data.avatar }); 
            setMessage({ type: 'success', text: 'Photo de profil mise à jour !' });
        } catch (err) {
            console.error("Détails erreur upload:", err.response?.data);
            setMessage({ 
                type: 'error', 
                text: err.response?.data?.message || 'Erreur lors de l\'envoi de l\'image' 
            });
        }
    };

    const handleUpdate = async (e) => {
        if (e) e.preventDefault();
        setMessage({ type: '', text: '' });

        const dataToSend = { ...formData };
        if (!dataToSend.new_password) {
            delete dataToSend.current_password;
            delete dataToSend.new_password;
            delete dataToSend.new_password_confirmation;
        }

        try {
            await api.put('/user/update', dataToSend);
            setMessage({ type: 'success', text: 'Profil mis à jour avec succès !' });
            setIsEditing(false);
            fetchUser(); 
        } catch (err) {
            setMessage({ 
                type: 'error', 
                text: err.response?.data?.message || 'Erreur lors de la mise à jour' 
            });
        }
    };

    const handleDeleteAccount = async () => {
        try {
            await api.delete('/user', { data: { delete_content: deleteContent } });
            localStorage.removeItem('auth_token');
            window.location.href = '/login';
        } catch (err) {
            setMessage({ type: 'error', text: 'Erreur lors de la suppression du compte' });
            setShowDeleteModal(false);
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-slate-900 via-purple-900 to-slate-900 p-8 flex justify-center items-start overflow-y-auto relative">
            
            {/* BOUTON RETOUR (Positionné en haut à gauche de la page) */}
            <button 
                onClick={() => navigate('/')} 
                className="fixed top-8 left-8 flex items-center gap-2 px-4 py-2 bg-white/10 hover:bg-white/20 backdrop-blur-md border border-white/20 text-white rounded-xl transition-all shadow-lg z-10"
            >
                <span>←</span>
                <span className="text-sm font-medium">Retour</span>
            </button>

            <div className="max-w-2xl w-full bg-white/10 backdrop-blur-xl rounded-3xl border border-white/20 p-10 shadow-2xl my-10 relative">
                
                {/* Header & Avatar */}
                <div className="flex flex-col items-center mb-10">
                    <div className="relative group">
                        {user?.profil_pic ? (
                            <img 
                                src={user.profil_pic.startsWith('http') ? user.profil_pic : `http://localhost:8080${user.profil_pic}`} 
                                className="w-32 h-32 rounded-full border-4 border-purple-500/50 shadow-xl object-cover" 
                                alt="Profile" 
                            />
                        ) : (
                            <div className="w-32 h-32 rounded-full bg-gradient-to-tr from-purple-500 to-pink-500 flex items-center justify-center text-3xl font-bold text-white shadow-xl border-4 border-white/10">
                                {getInitials()}
                            </div>
                        )}
                        <label className="absolute bottom-0 right-0 bg-purple-600 p-2.5 rounded-full cursor-pointer hover:bg-purple-500 transition-all shadow-lg border border-white/20">
                            <input 
                                type="file" 
                                className="hidden" 
                                onChange={handleAvatarChange}
                                accept="image/*"
                            />
                            <span className="text-white text-sm">📸</span>
                        </label>
                    </div>
                    <h2 className="text-white text-3xl font-bold mt-4 tracking-tight">
                        {user?.firstName} {user?.lastName}
                    </h2>
                    <p className="text-purple-200/50 text-sm">Membre Connect'In</p>
                </div>

                {message.text && (
                    <div className={`mb-6 p-4 rounded-xl text-sm border ${message.type === 'success' ? 'bg-green-500/20 border-green-500/50 text-green-200' : 'bg-red-500/20 border-red-500/50 text-red-200'}`}>
                        {message.text}
                    </div>
                )}

                <form onSubmit={handleUpdate} className="space-y-6">
                    <div className="grid grid-cols-2 gap-4">
                        <div className="space-y-2">
                            <label className="text-purple-100 text-sm ml-1">Prénom</label>
                            <input 
                                type="text" disabled={!isEditing}
                                className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl text-white outline-none focus:ring-2 focus:ring-purple-400 transition-all disabled:opacity-50"
                                value={formData.firstName}
                                onChange={e => setFormData({...formData, firstName: e.target.value})}
                            />
                        </div>
                        <div className="space-y-2">
                            <label className="text-purple-100 text-sm ml-1">Nom</label>
                            <input 
                                type="text" disabled={!isEditing}
                                className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl text-white outline-none focus:ring-2 focus:ring-purple-400 transition-all disabled:opacity-50"
                                value={formData.lastName}
                                onChange={e => setFormData({...formData, lastName: e.target.value})}
                            />
                        </div>
                    </div>

                    <div className="space-y-2">
                        <label className="text-purple-100 text-sm ml-1">Email professionnel</label>
                        <input 
                            type="email" disabled={!isEditing}
                            className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl text-white outline-none focus:ring-2 focus:ring-purple-400 transition-all disabled:opacity-50"
                            value={formData.email}
                            onChange={e => setFormData({...formData, email: e.target.value})}
                        />
                    </div>

                    {isEditing && (
                        <div className="pt-4 space-y-4 border-t border-white/10 animate-in fade-in slide-in-from-top-2">
                            <p className="text-purple-300 text-xs font-semibold uppercase tracking-wider">Sécurité du compte</p>
                            
                            <div className="space-y-1">
                                <label className="text-white/60 text-xs ml-1">Mot de passe actuel (requis pour modifier le profil)</label>
                                <input 
                                    type="password" 
                                    placeholder="••••••••"
                                    className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl text-white outline-none focus:ring-2 focus:ring-purple-400 transition-all"
                                    value={formData.current_password}
                                    onChange={e => setFormData({...formData, current_password: e.target.value})}
                                />
                            </div>

                            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <div className="space-y-1">
                                    <label className="text-white/60 text-xs ml-1">Nouveau mot de passe</label>
                                    <input 
                                        type="password" 
                                        placeholder="Nouveau"
                                        className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl text-white outline-none focus:ring-2 focus:ring-purple-400 transition-all"
                                        value={formData.new_password}
                                        onChange={e => setFormData({...formData, new_password: e.target.value})}
                                    />
                                </div>
                                <div className="space-y-1">
                                    <label className="text-white/60 text-xs ml-1">Confirmer le nouveau</label>
                                    <input 
                                        type="password" 
                                        placeholder="Confirmation"
                                        className="w-full px-4 py-3 bg-white/5 border border-white/10 rounded-xl text-white outline-none focus:ring-2 focus:ring-purple-400 transition-all"
                                        value={formData.new_password_confirmation}
                                        onChange={e => setFormData({...formData, new_password_confirmation: e.target.value})}
                                    />
                                </div>
                            </div>
                        </div>
                    )}

                    <div className="flex gap-4 pt-4">
                        {!isEditing ? (
                            <button 
                                type="button" 
                                onClick={(e) => { e.preventDefault(); setIsEditing(true); }}
                                className="flex-1 bg-white/10 hover:bg-white/20 text-white font-semibold py-3 rounded-xl transition-all border border-white/20"
                            >
                                Modifier mes informations
                            </button>
                        ) : (
                            <>
                                <button 
                                    type="submit"
                                    className="flex-1 bg-purple-600 hover:bg-purple-500 text-white font-semibold py-3 rounded-xl shadow-lg transition-all"
                                >
                                    Enregistrer
                                </button>
                                <button 
                                    type="button" 
                                    onClick={() => { setIsEditing(false); fetchUser(); }}
                                    className="flex-1 bg-white/5 hover:bg-white/10 text-white py-3 rounded-xl transition-all border border-white/10"
                                >
                                    Annuler
                                </button>
                            </>
                        )}
                    </div>
                </form>

                <div className="mt-12 pt-8 border-t border-white/10">
                    <h3 className="text-red-400 font-bold mb-4 flex items-center gap-2">
                        <span>⚠️</span> Zone de danger
                    </h3>
                    <div className="bg-red-500/10 border border-red-500/20 rounded-2xl p-6 flex items-center justify-between">
                        <div>
                            <p className="text-red-100 font-medium">Supprimer le compte</p>
                            <p className="text-red-200/50 text-xs">Cette action est irréversible.</p>
                        </div>
                        <button 
                            type="button"
                            onClick={() => setShowDeleteModal(true)}
                            className="bg-red-600 hover:bg-red-500 text-white text-sm font-bold py-2.5 px-5 rounded-xl transition-all shadow-lg"
                        >
                            Supprimer
                        </button>
                    </div>
                </div>
            </div>

            {showDeleteModal && (
                <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-950/80 backdrop-blur-sm">
                    <div className="bg-slate-900 border border-white/10 p-8 rounded-3xl max-w-md w-full shadow-2xl">
                        <h3 className="text-xl font-bold text-white mb-4">Supprimer mon compte ?</h3>
                        <p className="text-slate-400 text-sm mb-6">Toutes vos données seront définitivement effacées.</p>
                        <div className="flex gap-3">
                            <button onClick={handleDeleteAccount} className="flex-1 bg-red-600 text-white font-bold py-3 rounded-xl hover:bg-red-500 transition-colors">Confirmer</button>
                            <button onClick={() => setShowDeleteModal(false)} className="flex-1 bg-white/5 text-white py-3 rounded-xl hover:bg-white/10 transition-colors">Annuler</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Profile;