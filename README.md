# Calm Pulses Backend

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?logo=java" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot" />
  <img src="https://img.shields.io/badge/MySQL-8+-blue?logo=mysql" />
  <img src="https://img.shields.io/badge/Gradle-Build-green?logo=gradle" />
  <img src="https://img.shields.io/badge/JWT-Security-orange?logo=jsonwebtokens" />
  <img src="https://img.shields.io/badge/OpenAPI-Swagger-yellow?logo=swagger" />
</p>

---

## 🏗️ High-Level Architecture

```mermaid
graph LR
    A[User] -->|creates| B[Journal]
    A -->|creates| C[Reminder]
    A -->|checks in| D[Mood]
    B -->|has| E[Attachment]
    C -->|has| F[ReminderDays]
    D -->|has| G[MoodType]
    A -->|has| H[UserStats]
    H -->|tracks| I[JournalEntries]
    H -->|tracks| J[MoodCheckIns]
    H -->|tracks| K[ResourcesUsed]
    H -->|tracks| L[Achievements]
```

---

## 🗄️ Database ER Diagram

```mermaid
erDiagram
    USERS ||--o{ JOURNALS : has
    USERS ||--o{ MOODS : "checks in"
    USERS ||--o{ REMINDERS : sets
    USERS ||--|| USER_STATS : owns
    USERS ||--o{ TOKEN : has
    JOURNALS }o--|| MOODS : "linked mood"
    JOURNALS }o--|| ATTACHMENT : "has"
    MOODS }o--|| LOCATION : "recorded at"
    REMINDERS }o--|| REMINDER_DAYS : "on days"
    USER_STATS }o--o{ ACHIEVEMENTS : "earns"
```

---

## 🔐 Security Flow (JWT Authentication)

```mermaid
sequenceDiagram
    participant U as User
    participant FE as Frontend
    participant BE as Backend
    participant DB as Database
    U->>FE: Login/Register
    FE->>BE: POST /auth (credentials)
    BE->>DB: Validate user
    BE-->>FE: JWT Access & Refresh Token
    FE->>BE: Authenticated API Request (JWT)
    BE->>DB: Validate JWT & fetch data
    BE-->>FE: Response
```

---

## 🗃️ Database Schema (Main Entities)

- **User** (`users`):
  - id (UUID, PK)
  - name, email, password, avatarURL, phoneNumber, address
  - joinedDate, lastLogin, lastUpdated, role
- **Journal** (`journals`):
  - id (PK), prompt, mood (FK), title, description, attachment, createdAt, user (FK)
- **Mood** (`moods`):
  - id (PK), moodType, value, note, timeStamp, location, user (FK)
- **Reminder** (`reminders`):
  - id (PK), title, time, days, enabled, lastTriggered, user (FK)
- **UserStats** (`user_stats`):
  - id (PK), journalEntries, moodCheckIns, resourcesUsed, averageMood, longestStreak, currentStreak, level, achievement, user (FK)
- **Token** (`token`):
  - id (PK), token, tokenType, revoked, expired, user (FK)

---

## 🛡️ Security Mechanism

- **Authentication:** JWT-based, stateless sessions
- **Authorization:** Role-based (USER, ADMIN), method-level security
- **Password Storage:** BCrypt hashing
- **Token Management:** Access & refresh tokens, token revocation
- **Spring Security** with custom filters and providers

---

## 🚀 Technical Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Database:** MySQL 8+
- **ORM:** Spring Data JPA (Hibernate)
- **Security:** Spring Security, JWT
- **API Docs:** OpenAPI/Swagger
- **Validation:** Jakarta Validation
- **Mapping:** ModelMapper
- **Build Tool:** Gradle

---

## ⚙️ Installation Guide

1. **Clone the repository:**
   ```powershell
   git clone https://github.com/sanketp1/calm-pulse-backend.git
   cd calm-puleses-backend
   ```
2. **Configure Database:**
   - Update `src/main/resources/application.yml` with your MySQL credentials if needed.
3. **Build the project:**
   ```powershell
   ./gradlew build
   ```
4. **Run the application:**
   ```powershell
   ./gradlew bootRun
   ```
5. **API Documentation:**
   - Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) after starting the server.

---

## Made by Sanket with ❤️
