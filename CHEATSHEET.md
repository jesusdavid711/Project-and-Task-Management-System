# 🛠️ Command Cheatsheet & Setup Guide

Since this project has a nested folder structure, follow these commands step-by-step when you clone the repository on a new machine.

## 📂 Project Structure
When you clone the repo, you will see:
```text
Project-Root/
├── README.md
├── CHEATSHEET.md  <-- You are here
└── SGPT/          <-- The actual Spring Boot Project (Backend + Frontend Code)
    ├── compose.yaml
    ├── Dockerfile
    ├── pom.xml
    └── src/
```

---

## 🚀 1. Getting Started (First Time)
Always navigate to the inner `SGPT` folder first.

**Terminal:**
```bash
# 1. Enter the project directory
cd SGPT
```

---

## 🐳 2. Running with Docker (Recommended)
This is the easiest way to run everything (Backend + Database + Frontend).

**Start everything:**
```bash
# Rebuilds and starts the containers in the background
docker compose up -d --build
```
> **Note:** If you get a "permission denied" error on Linux, try using `sudo docker compose ...`.

**Check logs:**
```bash
docker compose logs -f app
```

**Stop everything:**
```bash
docker compose down
```

**Access the App:**
- **Frontend/App:** [http://localhost:8080](http://localhost:8080)
- **Swagger API:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 💻 3. Development Commands (Validation & Testing)
If you want to run tests or code without Docker.

**Verify Prerequisites:**
```bash
java -version   # Should be Java 17+
./mvnw -version # Checks Maven wrapper
```

**Run Unit Tests:**
```bash
# Runs the 5 required unit tests
./mvnw test
```

**Run the Application locally (without Docker):**
*Note: You need a running MySQL database on localhost:3307 or update application.properties.*
```bash
./mvnw spring-boot:run
```

**Build the JAR file:**
```bash
./mvnw clean package
```

---

## 🛠️ 4. Database Credentials
If you need to connect manually (via DBeaver or Workbench):

- **Host:** `localhost`
- **Port:** `3307` (External mapped port)
- **Database:** `SGPT`
- **Username:** `SGPT` (or `root`)
- **Password:** `Qwe.123*`

---

## 🐞 5. Troubleshooting common issues

**"Port 3306 or 8080 already in use"**
- Change the ports in `compose.yaml` (e.g., change `8080:8080` to `8081:8080`).

**"Connection Refused"**
- Ensure Docker containers aren't still starting up. Check `docker ps`.

**"403 Forbidden in Swagger but Front works"**
- The Docker container might be outdated. Run `docker compose up -d --build`.
