# 🌐 Connect'in — Plateforme de Networking

### 📝 Résumé du projet
**Connect'in** est une application web conçue pour faciliter la mise en relation professionnelle et sociale. Elle permet aux utilisateurs de créer un profil, de se connecter avec d'autres et d'échanger dans un environnement moderne et intuitif.

L'application est construite sur une architecture découplée :
- Un **backend** robuste basé sur Laravel qui expose une API RESTful.
- Un **frontend** réactif développé avec React, offrant une expérience utilisateur fluide.
- L'ensemble de l'environnement de développement est géré par Docker, assurant une installation et une exécution cohérentes.

---

### 🛠 Technologies utilisées
- **Backend** : PHP 8.2+ / Laravel 12.x
- **Frontend** : React 19+ (Vite), Tailwind CSS
- **Base de données** : MySQL 8.4
- **Environnement** : Docker (Laravel Sail)

---

### 📦 Installation et Configuration

Le projet utilise **Laravel Sail**, une interface en ligne de commande pour interagir avec l'environnement de développement Docker de Laravel. La seule dépendance requise sur votre machine locale est Docker et Docker Compose.

### 1. Cloner le projet
```bash
git clone https://github.com/votre-username/connect_in.git
cd connect_in/backend
```

### 2. Configuration de l'environnement
Le backend Laravel gère la configuration via un fichier `.env`. Copiez l'exemple fourni :

```bash
cp .env.example .env
```

Le fichier `compose.yaml` est déjà configuré pour lancer les services `laravel.test`, `mysql` et `frontend`. Les variables d'environnement pour la base de données (nom, utilisateur, mot de passe) sont lues depuis ce fichier `.env` et automatiquement provisionnées dans le conteneur MySQL. Vous pouvez les personnaliser si nécessaire.

### 3. Installation des dépendances
Lancez les conteneurs Docker. Sail installera automatiquement les dépendances `composer` (backend) et `npm` (frontend) lors du premier démarrage.

```bash
# Donnez les droits d'exécution au script Sail (Linux/macOS)
chmod +x vendor/bin/sail

# Démarrez les services (cela peut prendre quelques minutes la première fois)
./vendor/bin/sail up -d --build
```

### 4. Lancer les migrations de la base de données
Une fois les conteneurs démarrés, exécutez les migrations Laravel pour créer les tables de la base de données :
```bash
./vendor/bin/sail artisan migrate
```
Votre environnement est maintenant prêt !

---

### 🚀 Démarrage du projet

Pour démarrer tous les services (backend, frontend, base de données), exécutez simplement :

```bash
cd backend
./vendor/bin/sail up
```

L'application sera accessible aux adresses suivantes :
- **Frontend (React)** : http://localhost:5174
- **Backend (Laravel API)** : http://localhost:8080

Pour arrêter les services :
```bash
./vendor/bin/sail down
```

---

### 🖥️ Utilisation de l'API

L'API est exposée par le backend Laravel. Le frontend React est déjà configuré pour communiquer avec elle via l'URL spécifiée dans la variable d'environnement `VITE_API_URL` (gérée par Docker).

- **Définition des routes** : Les routes de l'API sont définies dans le fichier `backend/routes/api.php`. Vous pouvez y consulter tous les endpoints disponibles.

- **Authentification** : Le projet utilise Laravel Sanctum pour l'authentification. Les requêtes nécessitant une authentification doivent inclure un token d'API valide dans les en-têtes.

- **Exemple de requête (avec `curl`)**
  ```bash
  # Exemple pour récupérer la liste des utilisateurs (endpoint à adapter)
  curl -X GET http://localhost:8080/api/users
  ```

### 🖥️ Fonctionnalités par Page

L'application est découpée en plusieurs modules clés offrant une expérience collaborative complète :

---

#### 🔐 Authentification & Profil

- **Connexion / Inscription** : Interface sécurisée avec persistance des données utilisateur dans le `localStorage` pour une session fluide.
- **Gestion du Profil** : Personnalisation des informations utilisateur (Prénom, Nom, Mail, etc...).

---

#### 📱 Fil d'actualité (Feed)

- **Publication** : Partage de messages et de médias avec la communauté.
- **Interactions** : Système de "Likes" et de commentaires en temps réel sur les publications des autres utilisateurs.

---

#### 👥 Espaces d'Équipes (Teams)

C'est le cœur collaboratif de Connect'In, permettant de créer des groupes de travail ou d'intérêt.

- **Création d'Équipe** : Tout utilisateur peut devenir "Owner" d'un nouvel espace.
- **Gestion des Membres** :
    - **Propriétaire** : Peut ajouter ou supprimer des membres.
    - **Membres** : Peuvent rejoindre des discussions et quitter l'équipe.
- **Messagerie d'Équipe** : Chaque équipe possède son propre canal de discussion dédié pour échanger des posts exclusifs.
- **Zone Danger** : Le propriétaire dispose du contrôle total pour **supprimer définitivement une équipe**, entraînant le nettoyage de tous les messages associés.
---

### 📄 Copyright

© 2026 Antoine ALLARD & Alexandre Grandjean — Tous droits réservés.
