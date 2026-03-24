import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
    // On vérifie si le token est présent dans le localStorage ou non
    const token = localStorage.getItem('access_token');

    if (!token) 
    {
        // Si pas de token, on redirige vers le Login
        return <Navigate to="/login" replace />;
    }

    // Si le token existe, on affiche le composant enfant qui est la page demandée
    return children;
};

export default ProtectedRoute;