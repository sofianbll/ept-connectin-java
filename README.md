<div align="center">

<a href="https://github.com/Sofian-bll/ept-connectin-java/blob/main/LICENSE">
  <img src="https://img.shields.io/github/license/Sofian-bll/ept-connectin-java?style=flat" alt="License">
</a>
<a href="https://github.com/Sofian-bll/ept-connectin-java/releases">
  <img src="https://img.shields.io/github/v/release/Sofian-bll/ept-connectin-java?style=flat" alt="Version">
</a>
<a href="https://github.com/Sofian-bll/ept-connectin-java/stargazers">
  <img src="https://img.shields.io/github/stars/Sofian-bll/ept-connectin-java?style=flat" alt="Stars">
</a>

<br>

<img src="assets/logo.svg" alt="Connect'In Logo" width="300">

<h1 id="readme-top">Connect'In v2 — Spring Boot API</h1>

<p>
  <strong>Epitech project — Full backend migration from Laravel to Spring Boot.</strong><br>
  <em>Same frontend, new power under the hood.</em>
</p>

<p align="center">🇬🇧 <a href="README.md"><b>English</b></a> · 🇫🇷 <a href="README.fr.md">Français</a></p>

</div>

---

## What is this?

Connect'In is a professional social network built for Epitech. The original Laravel backend has been fully migrated to a **modular monolith Java Spring Boot API**, covering authentication (JWT), users, posts, comments, likes, and teams — all while keeping the existing Vue.js frontend **strictly unchanged**.

The goal: reproduce every route and payload faithfully while improving robustness, security, and code structure with Java Spring Boot.

---

## Built With

- [![Java](https://img.shields.io/badge/java%2025-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)](https://www.oracle.com/java/) — Runtime
- [![Spring Boot](https://img.shields.io/badge/Spring%20Boot%204-6DB33F.svg?style=flat&logo=Spring-Boot&logoColor=white)](https://spring.io/projects/spring-boot) — Backend framework
- [![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=flat&logo=Apache%20Maven&logoColor=white)](https://maven.apache.org/) — Build tool
- [![MySQL](https://img.shields.io/badge/mysql%208.4-%2300f.svg?style=flat&logo=mysql&logoColor=white)](https://www.mysql.com/) — Database
- [![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=flat&logo=docker&logoColor=white)](https://www.docker.com/) — Containerization
- [![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=flat&logo=vuedotjs&logoColor=%234FC08D)](https://vuejs.org/) — Frontend client (provided)

---

## Table of Contents

<details open>
<summary>Contents</summary>

- [Features & Architecture](#features--architecture)
- [Getting Started](#getting-started)
- [Seed Accounts](#seed-accounts)
- [API Documentation](#api-documentation)
- [API Routes](#api-routes)
- [Database Diagram](#database-diagram)
- [Project Structure](#project-structure)
- [License](#license)

</details>

---

## Features & Architecture

### Features

- **Authentication** — JWT-based register/login secured by Spring Security.
- **Users** — Profile management, avatar upload, search, account deletion/anonymization.
- **Social** — CRUD posts (with images and team scoping), comments, and likes.
- **Teams** — Create teams, manage members and roles.
- **Data** — Idempotent seeders for instant demo-ready data.

### Bonus

- Application-level caching with Spring (`@Cacheable` / `@CacheEvict`).
- Unit tests covered by JUnit + Mockito.
- Centralized JSON error handling via `@ControllerAdvice`.
- Full containerization (Dev & Prod) driven by a Makefile.

### Architecture

The application follows a domain-driven modular monolith — each business domain (auth, user, post, comment, like, team) is isolated with its own Controller, Service, Repository, and DTOs.

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

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## Getting Started

### Prerequisites

- **Docker Desktop** (for database and full deployment)
- **Node.js** (to run the provided frontend)

### Quick Start

```bash
git clone git@github.com:Sofian-bll/ept-connectin-java.git
cd connect-in-java
```

### Commands

A `Makefile` is provided for a smooth developer experience.

| Command | Description |
|---------|------------|
| `make dev` | Start development environment (hot-reload) |
| `make prod` | Start production environment |
| `make down` / `make down-dev` | Stop containers (Prod / Dev) |
| `make reset` / `make reset-dev` | Reset database (Prod / Dev) |
| `make clean` | Purge Docker images and volumes |
| `make logs` / `make logs-db` | Live logs (Backend / Database) |

**Without Makefile (Docker Compose directly):**

```bash
# Development
docker compose -f docker-compose.dev.yml up --build --force-recreate

# Production
docker compose up --build --force-recreate
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## Seed Accounts

Use these pre-seeded accounts for immediate testing:

| Email | Password |
|---|---|
| `sofian@test.com` | `password123` |
| `alice@test.com` | `password123` |
| `bob@test.com` | `password123` |
| `shanisya@test.com` | `password123` |

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## API Documentation

Swagger UI is available locally once the application is running.

| Resource | Link |
|----------|------|
| Swagger UI | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) |
| OpenAPI JSON | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |

**Exported documentation:**

| Document | PDF | Markdown |
|----------|-----|----------|
| API Documentation | [API.pdf](Resources/docs/ApiDocumentation.pdf) | [API.md](Resources/notes/ApiDocumentation.md) |
| Connect'In v2 | [V2.pdf](Resources/docs/ConnectInVersion2.pdf) | [V2.md](Resources/notes/ConnectInVersion2.md) |
| Project Subject | [Sujet.pdf](Resources/docs/project.pdf) | [Sujet.md](Resources/notes/project.md) |

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## API Routes

<details>
<summary><b>Auth & Users</b></summary>

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/register` | Create account |
| `POST` | `/api/login` | Get JWT token |
| `GET` | `/api/user` | Get current user profile |
| `PUT` | `/api/user/update` | Update profile |
| `POST` | `/api/user/avatar` | Upload avatar |
| `DELETE` | `/api/user` | Delete account |
| `GET` | `/api/users/search?q=` | Search users |

</details>

<details>
<summary><b>Posts & Comments</b></summary>

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/posts` | List posts (supports `?team_id=` filter) |
| `GET` | `/api/posts/{id}` | Get post details |
| `POST` | `/api/posts` | Create post (`multipart`) |
| `PUT` | `/api/posts/{id}` | Update post |
| `DELETE` | `/api/posts/{id}` | Delete post |
| `GET` | `/api/posts/{postId}/comments` | List post comments |
| `POST` | `/api/posts/{postId}/comments` | Add comment |
| `PUT` | `/api/comments/{id}` | Update comment |
| `DELETE` | `/api/comments/{id}` | Delete comment |

</details>

<details>
<summary><b>Likes & Teams</b></summary>

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/posts/{id}/like` | Toggle like status |
| `GET` | `/api/teams` | List teams |
| `POST` | `/api/teams` | Create team |
| `GET` | `/api/teams/{id}` | Get team details |
| `DELETE` | `/api/teams/{id}` | Delete team |
| `POST` | `/api/teams/{id}/members` | Add team member |
| `DELETE` | `/api/teams/{id}/members/{memberId}` | Remove team member |

</details>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## Database Diagram

<p align="center">
  <img src="Resources/images/db/db-diagram-1.jpg" alt="Database Diagram" width="900">
  <br><em>Alternative view available <a href="Resources/images/db/db-diagram-2.jpg">here</a>.</em>
</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## Project Structure

```text
├── backend/        # Spring Boot API source code
├── frontend/       # Client source code (do not modify)
├── Resources/      # Documentation, DB diagrams, screenshots, Bruno collections
├── storage/        # Uploaded files (avatars, post images)
├── assets/         # Logo & assets
├── docker-compose.yml
├── docker-compose.dev.yml
└── Makefile
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

<!-- REFERENCE_LINKS -->
[license-shield]: https://img.shields.io/github/license/Sofian-bll/ept-connectin-java?style=flat
[version-shield]: https://img.shields.io/github/v/release/Sofian-bll/ept-connectin-java?style=flat
[stars-shield]: https://img.shields.io/github/stars/Sofian-bll/ept-connectin-java?style=flat
