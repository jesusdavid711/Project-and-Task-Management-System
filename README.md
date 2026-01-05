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

## 🏗️ Technical Decisions & Architecture

This solution implements **Clean Architecture** with a strict **Hexagonal (Ports & Adapters)** approach to decouple the core domain from external frameworks.

### 1. Domain Layer (The Core)
- **Pure Java:** No Spring/JPA annotations on domain models (`Project`, `Task`, `User`).
- **Use Case Driven:** Logic resides in Use Case implementations (e.g., `ActivateProjectUseCaseImpl`), representing business actions, not CRUD.
- **Rules:**
    - *Activation:* Requires at least 1 task.
    - *Completion:* Only owners can complete tasks.
    - *Soft Delete:* Entities are marked named `deleted`, never physically removed.

### 2. Infrastructure Layer (The Adapters)
- **Persistence:** `ProjectRepositoryAdapter` translates Domain calls to JPA repositories.
- **Security:** Spring Security + JWT. Exception handling via custom `EntryPoint` to ensure cleaner JSON errors (401/403) instead of HTML.
- **Notifications/Audit:** Implemented as adapters (Console logging for demo efficiency).

### 3. Frontend
- **Simple SPA:** Vanilla HTML/CSS/JS.
- **No Build Tools:** Designed to run directly from Spring Boot static resources for simplicity and portability.
- **Design:** Modern CSS variables, rounded corners, and toast notifications for a polished feel.

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
