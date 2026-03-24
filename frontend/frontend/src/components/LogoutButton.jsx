import React from 'react';
import { useNavigate } from 'react-router-dom';

const LogoutButton = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // On supprime le token du stockage local
        localStorage.removeItem('access_token');
        
        // On redirige vers le login
        navigate('/login');
    };

    return (
        <button 
            onClick={handleLogout}
            style={{
                padding: '8px 15px',
                backgroundColor: '#ff4444',
                color: 'white',
                border: 'none',
                borderRadius: '5px',
                cursor: 'pointer'
            }}
        >
            Déconnexion
        </button>
    );
};

export default LogoutButton;