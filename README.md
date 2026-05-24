<div align="center">
  <img src="Resources/images/ConnectIn-logo.svg" alt="Connect'In Logo" width="360">

  <h1>Connect'In v2 — Spring Boot API</h1>

  <p>
    <strong>Migration complete du backend Laravel vers Spring Boot.</strong><br>
    <em>Meme frontend, nouvelle puissance sous le capot.</em>
  </p>

  <p>
    <a href="#stack-technique">Stack Technique</a> •
    <a href="#getting-started">Installation</a> •
    <a href="#api-documentation">Documentation</a> •
    <a href="#api-routes">Endpoints</a>
  </p>
</div>

> [!IMPORTANT]
> **Contrainte Majeure :** Le frontend fourni doit rester **strictement inchange**.
> L'objectif de cette API est de reproduire fidelement les routes et payloads attendus par le client existant, tout en ameliorant la robustesse, la securite et la structure du code via Java Spring Boot.

---

## 🛠️ Stack Technique

<div align="center">
  <img src="https://img.shields.io/badge/java%2025-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 25">
  <img src="https://img.shields.io/badge/Spring%20Boot%204-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white" alt="Spring Boot 4">
  <img src="https://img.shields.io/badge/mysql%208.4-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL 8.4">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Maven">
</div>

---

## ✨ Features & Architecture

<table width="100%">
  <tr>
    <td valign="top" width="50%">
      <h3>🎯 Fonctionnalites Cles</h3>
      <ul>
        <li><b>Authentification</b> : JWT (Register/Login) securise par Spring Security.</li>
        <li><b>Utilisateurs</b> : Profil, upload d'avatar, recherche, suppression/anonymisation.</li>
        <li><b>Social</b> : CRUD des posts (avec images et gestion d'equipe), commentaires et likes.</li>
        <li><b>Equipes</b> : Creation, gestion des membres et des roles.</li>
        <li><b>Data</b> : Seeders de donnees idempotents pour une demo immediate.</li>
      </ul>
    </td>
    <td valign="top" width="50%">
      <h3>🚀 Les Plus (Bonus)</h3>
      <ul>
        <li>Cache applicatif avec Spring (<code>@Cacheable</code> / <code>@CacheEvict</code>).</li>
        <li>Tests unitaires couverts par JUnit + Mockito.</li>
        <li>Gestion d'erreurs JSON uniformisee et globale.</li>
        <li>Conteneurisation complete (Dev & Prod) pilotee par Makefile.</li>
      </ul>
    </td>
  </tr>
</table>

<details>
  <summary><b>🏗️ Voir l'Architecture (Modular Monolith)</b></summary>

L'application est structuree par domaine metier, isolant chaque composant (Controller, Service, Repository, DTO) dans un module coherent tout en conservant un deploiement unique :

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

</details>

---

## 🚀 Getting Started

### ⚙️ Prerequisites

- **Docker Desktop** (pour la BDD et le deploiement)
- **Node.js** (pour lancer le frontend fourni)

### 📥 Quick Start

```bash
git clone <PRIVATE_REPO_URL>
cd connect-in-java
```

### 🛠️ Commands

Un `Makefile` est fourni pour simplifier l'experience developpeur (DX).

<table width="100%">
  <tr>
    <th width="30%">Commande</th>
    <th width="70%">Description</th>
  </tr>
  <tr>
    <td align="center"><kbd>make dev</kbd></td>
    <td>Lance l'environnement de developpement (avec hot-reload)</td>
  </tr>
  <tr>
    <td align="center"><kbd>make prod</kbd></td>
    <td>Lance l'environnement de production</td>
  </tr>
  <tr>
    <td align="center"><kbd>make down</kbd> / <kbd>make down-dev</kbd></td>
    <td>Stoppe les conteneurs (Prod / Dev)</td>
  </tr>
  <tr>
    <td align="center"><kbd>make reset</kbd> / <kbd>make reset-dev</kbd></td>
    <td>Reinitialise la base de donnees (Prod / Dev)</td>
  </tr>
  <tr>
    <td align="center"><kbd>make clean</kbd></td>
    <td>Purge les images et les volumes Docker</td>
  </tr>
  <tr>
    <td align="center"><kbd>make logs</kbd> / <kbd>make logs-db</kbd></td>
    <td>Affiche les logs en direct (Backend / BDD)</td>
  </tr>
</table>

<details>
  <summary><b>Alternative sans Makefile (Docker Compose)</b></summary>

```bash
# Development
docker compose -f docker-compose.dev.yml up --build --force-recreate

# Production
docker compose up --build --force-recreate
```

</details>

---

## 🔑 Seed Accounts

Pour tester l'application immediatement, utilisez ces comptes generes par les seeders :

| Email | Password |
|---|---|
| `sofian@test.com` | `password123` |
| `alice@test.com` | `password123` |
| `bob@test.com` | `password123` |
| `shanisya@test.com` | `password123` |

---

## 📚 API Documentation

Une fois l'application lancee, l'interface Swagger UI est disponible en local pour tester les routes.

<table width="100%">
  <tr>
    <th>Type</th>
    <th>Ressource</th>
    <th>Lien / Fichier</th>
  </tr>
  <tr>
    <td>Swagger local</td>
    <td>UI interactive</td>
    <td><a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a></td>
  </tr>
  <tr>
    <td>Swagger local</td>
    <td>JSON brut (OpenAPI)</td>
    <td><a href="http://localhost:8080/v3/api-docs">http://localhost:8080/v3/api-docs</a></td>
  </tr>
  <tr>
    <td>Apercu API</td>
    <td>Users / Admin</td>
    <td><a href="Resources/images/api/api-docs-users-admin.jpg">Voir l'image</a></td>
  </tr>
  <tr>
    <td>Apercu API</td>
    <td>Posts / Teams</td>
    <td><a href="Resources/images/api/api-docs-posts-teams.jpg">Voir l'image</a></td>
  </tr>
</table>

<details>
  <summary><b>📄 Voir les exports de documentation (PDF / Markdown)</b></summary>

<table width="100%">
  <tr>
    <th width="33%">Document</th>
    <th width="33%">Format PDF</th>
    <th width="33%">Format Markdown</th>
  </tr>
  <tr>
    <td align="center"><b>API Documentation</b></td>
    <td align="center"><a href="Resources/docs/ApiDocumentation.pdf">📄 API.pdf</a></td>
    <td align="center"><a href="Resources/notes/ApiDocumentation.md">📝 API.md</a></td>
  </tr>
  <tr>
    <td align="center"><b>Connect'In Version 2</b></td>
    <td align="center"><a href="Resources/docs/ConnectInVersion2.pdf">📄 V2.pdf</a></td>
    <td align="center"><a href="Resources/notes/ConnectInVersion2.md">📝 V2.md</a></td>
  </tr>
  <tr>
    <td align="center"><b>Sujet du projet</b></td>
    <td align="center"><a href="Resources/docs/project.pdf">📄 Sujet.pdf</a></td>
    <td align="center"><a href="Resources/notes/project.md">📝 Sujet.md</a></td>
  </tr>
</table>

</details>

---

## 🗄️ Database Diagram

<div align="center">
  <img src="Resources/images/db/db-diagram-1.jpg" alt="Database Diagram" width="900">
  <p><em>Vue alternative disponible <a href="Resources/images/db/db-diagram-2.jpg">ici</a>.</em></p>
</div>

---

## 🛣️ API Routes

<details>
  <summary><b>🔐 Auth & Users</b></summary>

<table width="100%">
  <tr>
    <th width="15%">Method</th>
    <th width="45%">Endpoint</th>
    <th width="40%">Description</th>
  </tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/register</code></td><td>Create account</td></tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/login</code></td><td>Get JWT token</td></tr>
  <tr><td align="center"><code>GET</code></td><td><code>/api/user</code></td><td>Get current user profile</td></tr>
  <tr><td align="center"><code>PUT</code></td><td><code>/api/user/update</code></td><td>Update profile</td></tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/user/avatar</code></td><td>Upload avatar</td></tr>
  <tr><td align="center"><code>DELETE</code></td><td><code>/api/user</code></td><td>Delete account</td></tr>
  <tr><td align="center"><code>GET</code></td><td><code>/api/users/search?q=</code></td><td>Search users</td></tr>
</table>

</details>

<details>
  <summary><b>📝 Posts & Comments</b></summary>

<table width="100%">
  <tr>
    <th width="15%">Method</th>
    <th width="45%">Endpoint</th>
    <th width="40%">Description</th>
  </tr>
  <tr><td align="center"><code>GET</code></td><td><code>/api/posts</code></td><td>List posts (supports <code>?team_id=</code> filter)</td></tr>
  <tr><td align="center"><code>GET</code></td><td><code>/api/posts/{id}</code></td><td>Get post details</td></tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/posts</code></td><td>Create post (<code>multipart</code>)</td></tr>
  <tr><td align="center"><code>PUT</code></td><td><code>/api/posts/{id}</code></td><td>Update post</td></tr>
  <tr><td align="center"><code>DELETE</code></td><td><code>/api/posts/{id}</code></td><td>Delete post</td></tr>
  <tr><td align="center"><code>GET</code></td><td><code>/api/posts/{postId}/comments</code></td><td>List post comments</td></tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/posts/{postId}/comments</code></td><td>Add comment</td></tr>
  <tr><td align="center"><code>PUT</code></td><td><code>/api/comments/{id}</code></td><td>Update comment</td></tr>
  <tr><td align="center"><code>DELETE</code></td><td><code>/api/comments/{id}</code></td><td>Delete comment</td></tr>
</table>

</details>

<details>
  <summary><b>❤️ Likes & 🛡️ Teams</b></summary>

<table width="100%">
  <tr>
    <th width="15%">Method</th>
    <th width="45%">Endpoint</th>
    <th width="40%">Description</th>
  </tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/posts/{id}/like</code></td><td>Toggle like status</td></tr>
  <tr><td align="center"><code>GET</code></td><td><code>/api/teams</code></td><td>List teams</td></tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/teams</code></td><td>Create team</td></tr>
  <tr><td align="center"><code>GET</code></td><td><code>/api/teams/{id}</code></td><td>Get team details</td></tr>
  <tr><td align="center"><code>DELETE</code></td><td><code>/api/teams/{id}</code></td><td>Delete team</td></tr>
  <tr><td align="center"><code>POST</code></td><td><code>/api/teams/{id}/members</code></td><td>Add team member</td></tr>
  <tr><td align="center"><code>DELETE</code></td><td><code>/api/teams/{id}/members/{memberId}</code></td><td>Remove team member</td></tr>
</table>

</details>

---

## 📁 Project Structure

```text
├── backend/        # Code source de l'API Spring Boot
├── frontend/       # Code source du client (Ne pas modifier)
└── Resources/      # Documentation, Diagrammes DB, Captures d'ecran, Collections Bruno
```
