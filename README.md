# W-JAV-101-PAR-1-5-connect_in_v2-3


# Connect'In V2

REST API for Connect'In v2 — migration from Laravel to Java Spring Boot.

---

## Tech Stack

- Java 25
- Spring Boot 4.0
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL
- Docker + Docker Compose

---

## Prerequisites

- Docker Desktop installed and running
- Node.js installed (for the frontend)

---

## Installation & Setup

### 1. Clone the repository

git clone https://github.com/EpitechWebAcademiePromo2027/W-JAV-101-PAR-1-5-connect_in_v2-3.git
cd W-JAV-101-PAR-1-5-connect_in_v2-3

### 2. Start the backend + database

docker compose up --build

Wait until you see `Started BackendApplication` in the logs.

### 3. Start the frontend

Open a second terminal:

cd frontend
npm install
npm run dev

---

## Access the app

| Service | URL |
|---|---|
| Frontend | http://localhost:5173 |
| Backend API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| API Docs JSON | http://localhost:8080/v3/api-docs |

---

## Database

The database runs automatically via Docker. No manual setup needed.

See `Resources/ConnectInVersion2.pdf` for the full database schema.

---

## API Documentation

Full interactive API documentation available at:
http://localhost:8080/swagger-ui/index.html

See `Resources/ApiDocumentation.pdf` for the exported documentation.

---

## Project Structure

backend/         → Spring Boot API
frontend/        → Frontend (from selected group)
Resources/       → Database diagram + API documentation
docker-compose.yml