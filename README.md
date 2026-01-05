# Project and Task Management System

**A robust, Hexagonal Architecture-based backend with a functional frontend, developed for high employability standards.**

---

## 📋 Requirements & Technologies
The project is built with specific versions to ensure stability and modern standards:

### 🛠 Tech Stack (Core)
*   **Language:** Java 17 (LTS)
*   **Framework:** Spring Boot 3.4.1
*   **Build Tool:** Maven 3.8+
*   **Security:** Spring Security 6 + JWT (jjwt 0.12.6)

### 🗄️ Database
*   **Engine:** MySQL 8.0
*   **Persistence:** Spring Data JPA (Hibernate)

### 🐳 Infrastructure
*   **Containerization:** Docker & Docker Compose
*   **API Documentation:** Swagger / OpenAPI (Springdoc 2.7.0)

---

## ✨ Features (What does it do?)
This system implements a full Lifecycle for Project Management:

1.  **User Management:**
    *   Secure Registration & Login (JWT).
    *   Password Encryption (BCrypt).

2.  **Project Management:**
    *   **Create:** Users can start new projects (Status: *DRAFT*).
    *   **Activate:** Transition from *DRAFT* to *ACTIVE* (Only if tasks exist).
    *   **Delete:** Soft-delete (logical removal) for safety.

3.  **Task Management:**
    *   **Add:** Tasks linked to specific projects.
    *   **Complete:** Mark as done (Audit trail generated).
    *   **Security:** Users can strictly ONLY manage their own data.

---

## 🚀 Quick Start (How to Run)

### 1. Prerequisites
- **Docker Deskstop** (latest version recommended)
- **Git**

### 2. Execution
Navigate to the project folder:
```bash
cd SGPT
docker compose up -d --build
```
*Wait ~20 seconds for MySQL to initialize.*

### 3. Access
- **Frontend Dashboard:** [http://localhost:8080](http://localhost:8080)
- **Swagger Documentation:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🔑 Demo Credentials (Testing)
The database auto-initializes with these users:

| Username | Password | User Type |
|----------|----------|-----------|
| `admin`  | `123456` | User with example data |
| `user`   | `123456` | New user (Empty workspace) |

> **Context:** There are no admin roles. Every user is isolated and manages only their own workspace.

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
