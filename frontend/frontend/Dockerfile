# Image Node légère
FROM node:20-alpine

# Dossier de travail dans le conteneur
WORKDIR /app

# On copie d'abord les fichiers de dépendances (cache Docker optimisé)
COPY package*.json ./

# Installation des dépendances
RUN npm ci

# On copie le reste du projet
COPY . .

# Vite écoute par défaut sur 5173
EXPOSE 5173

# Lancement du serveur Vite en mode "accessible depuis l'extérieur"
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "5173"]