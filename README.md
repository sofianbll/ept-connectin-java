<div align="center">
  <img src="Resources/images/ConnectIn-logo.svg" alt="Connect'In Logo" width="360">

  <h1>Connect'In v2 — Spring Boot API</h1>

  <p>
    <strong>Migration complète du backend Laravel vers Spring Boot.</strong><br>
    <em>Même frontend, nouvelle puissance sous le capot.</em>
  </p>

  <p>
    <a href="#-getting-started">Installation</a> •
    <a href="#-api-documentation">Documentation API</a> •
    <a href="#-api-routes">Endpoints</a>
  </p>
</div>

<br>

> **⚠️ Contrainte Majeure :** Le frontend fourni doit rester **strictement inchangé**. L'objectif de cette API est de reproduire fidèlement les routes et payloads attendus par le client existant, tout en améliorant la robustesse, la sécurité et la structure du code via Java Spring Boot.

---

## 🛠️ Stack Technique

<p align="center">
  <img src="https://img.shields.io/badge/java%2025-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 25">
  <img src="https://img.shields.io/badge/Spring%20Boot%204-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white" alt="Spring Boot 4">
  <img src="https://img.shields.io/badge/mysql%208.4-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL 8.4">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Maven">
</p>

---

## ✨ Features & Architecture

### Fonctionnalités Clés
* **Authentification** : JWT (Register/Login) sécurisé par Spring Security.
* **Utilisateurs** : Profil, upload d'avatar, recherche, suppression/anonymisation.
* **Social** : CRUD des Posts (avec images et gestion d'équipe), Commentaires et Likes.
* **Équipes** : Création, gestion des membres et des rôles.
* **Data** : Seeders de données idempotents pour une démo immédiate.

### 🚀 Les "Plus" (Bonus)
* Cache applicatif avec Spring (`@Cacheable` / `@CacheEvict`).
* Tests unitaires couverts par JUnit + Mockito.
* Gestion d'erreurs JSON uniformisée et globale.
* Conteneurisation complète (Dev & Prod) pilotée par Makefile.

### 🏗️ Architecture (Modular Monolith)
L'application est structurée par **domaine métier**, isolant chaque composant (Controller, Service, Repository, DTO) dans un module cohérent tout en conservant un déploiement unique :
```text
backend/src/main/java/com/connect_in/backend/
 ├── auth/
 ├── user/
 ├── post/
 ├── comment/
 ├── like/
 ├── team/
 └── shared/
```

---

## 🚀 Getting Started

### Prérequis

- **Docker Desktop** (pour la BDD et le déploiement)
- **Node.js** (pour lancer le frontend fourni)

### Installation rapide

```bash
git clone <PRIVATE_REPO_URL>
cd connect-in-java
```

### Commandes (Makefile)

Un `Makefile` est fourni pour simplifier l'expérience développeur (DX).

| Commande | Description |
|:---:|:---:|
| `make dev` | Lance l'environnement de développement (avec Hot-Reload) |
| `make prod` | Lance l'environnement de production |
| `make down` / `make down-dev` | Stoppe les conteneurs (Prod / Dev) |
| `make reset` / `make reset-dev`| Réinitialise la base de données (Prod / Dev) |
| `make clean` | Purge les images et les volumes Docker |
| `make logs` / `make logs-db` | Affiche les logs en direct (Backend / BDD) |

*Alternative sans Makefile :*

```bash
# Développement
docker compose -f docker-compose.dev.yml up --build --force-recreate

# Production
docker compose up --build --force-recreate
```

---

## 🔑 Seed Accounts

Pour tester l'application immédiatement, utilisez ces comptes générés par les seeders :

* 📧 `sofian@test.com` | 🔒 `password123`
* 📧 `alice@test.com` | 🔒 `password123`
* 📧 `bob@test.com` | 🔒 `password123`
* 📧 `shanisya@test.com` | 🔒 `password123`

---

## 📚 API Documentation

Une fois l'application lancée, l'interface **Swagger UI** est disponible en local pour tester les routes :

| Type | Ressource | Lien |
|:---:|:---:|:---:|
| Swagger local | UI interactive | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) |
| Swagger local | JSON brut (OpenAPI) | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |
| Aperçu API | Users / Admin | [Resources/images/api/api-docs-users-admin.jpg](Resources/images/api/api-docs-users-admin.jpg) |
| Aperçu API | Posts / Teams | [Resources/images/api/api-docs-posts-teams.jpg](Resources/images/api/api-docs-posts-teams.jpg) |
| Export documentation | PDF API | [Resources/docs/ApiDocumentation.pdf](Resources/docs/ApiDocumentation.pdf) |
| Export documentation | Markdown API | [Resources/notes/ApiDocumentation.md](Resources/notes/ApiDocumentation.md) |

### 📄 Documentation Projet

| Document | Format PDF | Format Markdown |
|:---:|:---:|:---:|
| Connect'In Version 2 | [Resources/docs/ConnectInVersion2.pdf](Resources/docs/ConnectInVersion2.pdf) | [Resources/notes/ConnectInVersion2.md](Resources/notes/ConnectInVersion2.md) |
| Sujet du projet | [Resources/docs/project.pdf](Resources/docs/project.pdf) | [Resources/notes/project.md](Resources/notes/project.md) |

---

## 🗄️ Database Diagram

<p align="center">
  <img src="Resources/images/db/db-diagram-1.jpg" alt="Database Diagram" width="900">
</p>

Vue alternative : [Resources/images/db/db-diagram-2.jpg](Resources/images/db/db-diagram-2.jpg)

---

## 🛣️ API Routes

### 🔐 Auth & Users

| Method | Endpoint | Description |
|:---:|:---:|:---:|
| `POST` | `/api/register` | Create account |
| `POST` | `/api/login` | Get JWT token |
| `GET` | `/api/user` | Get current user profile |
| `PUT` | `/api/user/update` | Update profile |
| `POST` | `/api/user/avatar` | Upload avatar |
| `DELETE` | `/api/user` | Delete account |
| `GET` | `/api/users/search?q=` | Search users |

### 📝 Posts & Comments

| Method | Endpoint | Description |
|:---:|:---:|:---:|
| `GET` | `/api/posts` | List posts (supports `?team_id=` filter) |
| `GET` | `/api/posts/{id}` | Get post details |
| `POST` | `/api/posts` | Create post (`multipart`) |
| `PUT` | `/api/posts/{id}` | Update post |
| `DELETE` | `/api/posts/{id}` | Delete post |
| `GET` | `/api/posts/{postId}/comments` | List post comments |
| `POST` | `/api/posts/{postId}/comments` | Add comment |
| `PUT` | `/api/comments/{id}` | Update comment |
| `DELETE` | `/api/comments/{id}` | Delete comment |

### ❤️ Likes & 🛡️ Teams

| Method | Endpoint | Description |
|:---:|:---:|:---:|
| `POST` | `/api/posts/{id}/like` | Toggle like status |
| `GET` | `/api/teams` | List teams |
| `POST` | `/api/teams` | Create team |
| `GET` | `/api/teams/{id}` | Get team details |
| `DELETE` | `/api/teams/{id}` | Delete team |
| `POST` | `/api/teams/{id}/members` | Add team member |
| `DELETE` | `/api/teams/{id}/members/{memberId}` | Remove team member |

---

## 📁 Project Structure

```text
├── backend/        # Code source de l'API Spring Boot
├── frontend/       # Code source du client (Ne pas modifier)
└── Resources/      # Documentation, Diagrammes DB, Captures d'écran, Collections Bruno
```

