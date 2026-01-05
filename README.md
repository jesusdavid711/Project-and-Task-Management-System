# Project and Task Management System

**A robust, Hexagonal Architecture-based backend with a functional frontend, ready for deployment.**

> **Assessment Goal:** Professional solution focused on Clean Architecture, Business Rules, and Real-world Employability.

## 🚀 Quick Start (Running the App)

This project is containerized for simplicity. Follow these steps to run it immediately.

### 1. Prerequisites
- Docker & Docker Compose installed.
- Git installed.

### 2. Steps to Run
Navigate to the project folder (ensure you are inside `SGPT` if cloned with nested structure):

```bash
# 1. Enter the project directory
cd SGPT

# 2. Build and Start Backend + Database
docker compose up -d --build
```
*Wait ~15 seconds for MySQL to initialize.*

### 3. Access the Application
- **Frontend Dashboard:** [http://localhost:8080](http://localhost:8080)
- **API Documentation (Swagger):** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🔑 Test Credentials (Data Seed)

The database is pre-populated with these users for testing:

| Role | Username | Password |
|------|----------|----------|
| **Admin** | `admin` | `password` |
| **User** | `user` | `password` |

*You can also register new users freely via the Login screen.*

---

## 🏗️ Technical Decisions (Why this way?)

We prioritized **maintainability** and **clarity** over speed of writing code. Here is the "Why" behind our choices:

### 1. Hexagonal Architecture (The "Core" First)
Instead of building everything around the Database (tables first), we built it around the **Business Logic**.
- **Why?** If we ever need to switch from MySQL to Postgres, or from a Web API to a CLI, the "Core" (Domain) remains untouched. It makes the application **future-proof**.

### 2. Spring Security + JWT
We use **Stateless Authentication**. The server doesn't "remember" logged-in users; it just validates the signature of the Token they send.
- **Why?** It scales better. The server handles thousands of requests without needing memory for sessions.

### 3. Docker
We packaged the App and the Database into containers.
- **Why?** To avoid the classic *"It works on my machine but not yours"* problem. With Docker, if it runs here, it runs everywhere.

### 4. No Lombok / Explicit Java
We avoided libraries that auto-generate code (like Lombok) in the Domain.
- **Why?** To make the code **100% readable** by any Java developer without needing special IDE plugins. What you see is what you execute.

---

## 🧪 Testing Strategy

The project includes **5 Critical Unit Tests** (JUnit 5 + Mockito) validating business rules without loading the Spring Context (Fast & Isolated).

**To run tests manually:**
```bash
./mvnw test
```

### Coverage:
1. `ActivateProject_WithTasks_ShouldSucceed`
2. `ActivateProject_WithoutTasks_ShouldFail`
3. `ActivateProject_ByNonOwner_ShouldFail`
4. `CompleteTask_AlreadyCompleted_ShouldFail`
5. `CompleteTask_ShouldGenerateAuditAndNotification`

---

## 📦 Project Structure

The project maps the layers of **Hexagonal Architecture** to the package structure:

```text
SGPT/
├── compose.yaml                # Docker Compose (App + MySQL)
├── Dockerfile                  # Multi-stage build
├── pom.xml                     # Maven dependencies
└── src/
    └── main/
        ├── java/com/projectmanager/
        │   ├── domain/         # 🟢 Enterprise Logic (No frameworks)
        │   │   ├── model/      # Entities (Project, Task, User)
        │   │   ├── port/       # Interfaces (In/Out)
        │   │   └── exception/  # Domain Exceptions
        │   │
        │   ├── application/    # 🟡 Application Logic
        │   │   └── usecase/    # Implementation of Input Ports
        │   │
        │   ├── infrastructure/ # 🔴 Frameworks & Drivers
        │   │   ├── config/     # Spring Config (Security, OpenAPI)
        │   │   ├── persistence/# JPA Entities & Repositories
        │   │   ├── security/   # JWT, Auth Filter, UserDetails
        │   │   └── adapter/    # Implementation of Output Ports
        │   │
        │   └── presentation/   # 🔵 Interface Layer
        │       ├── controller/ # REST Endpoints
        │       └── dto/        # Data Transfer Objects
        │
        └── resources/
            ├── application.properties # Config
            ├── data.sql               # Seed Data
            └── static/                # Frontend (HTML/JS/CSS)
```
