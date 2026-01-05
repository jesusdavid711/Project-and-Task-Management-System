# Project and Task Management System

A robust backend system for managing projects and tasks, built with Java 17, Spring Boot 3, and Clean Architecture (Hexagonal).

## 🚀 Features

- **Clean Architecture:** Strict separation of concerns (Domain, Application, Infrastructure, Presentation).
- **Hexagonal Architecture:** Ports and Adapters pattern.
- **Security:** JWT Authentication with Spring Security.
- **Database:** MySQL persistence using JPA.
- **Dockerized:** Ready to deploy with Docker Compose.
- **Frontend SPA:** Simple HTML/JS frontend for managing projects.

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.4.1
- Spring Security + JWT
- Spring Data JPA
- MySQL 8.0
- Docker & Docker Compose
- JUnit 5 + Mockito
- OpenAI / Swagger UI

## 🏁 Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 17 (optional, if running locally without Docker)

### Running with Docker (Recommended)

1. Clone the repository:
   ```bash
   git clone https://github.com/jesusdavid711/Project-and-Task-Management-System.git
   cd Project-and-Task-Management-System
   ```

2. Build and start the services:
   ```bash
   docker compose up --build
   ```

3. Access the application:
   - **Frontend:** http://localhost:8080
   - **Swagger UI:** http://localhost:8080/swagger-ui/index.html

### Manual Execution

1. Start MySQL database (or update `application.properties` to point to an existing DB).
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## 🧪 Testing

Run unit tests with Maven:
```bash
./mvnw test
```

## 🔑 Default Credentials / Test Users

You can register a new user via the Frontend or Swagger UI.

**Endpoint:** `POST /api/auth/register`

## 🏗️ Architecture

The project follows a strict Hexagonal Architecture:

- **Domain:** Pure Java business logic (Entities: `User`, `Project`, `Task`). No framework dependencies.
- **Application:** Use cases (`CreateProject`, `ActivateProject`, etc.) orchestration.
- **Infrastructure:** Framework implementations (JPA, Security, JWT). adapters implement Output Ports.
- **Presentation:** REST Controllers and DTOs.

## 📝 API Documentation

Complete API documentation is available via Swagger UI at `/swagger-ui/index.html` when the application is running.

## 🐳 Docker Configuration

- **Backend:** Multi-stage Dockerfile using `eclipse-temurin:17-jdk-alpine`.
- **Database:** MySQL 8.0 container.
- **Orchestration:** `compose.yaml` defines services, networks, and health checks.
