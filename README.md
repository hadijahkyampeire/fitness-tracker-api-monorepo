##  [Project / milestone management](https://github.com/hadijahkyampeire/fitness-tracker-api-monorepo/milestone/1)

## [Swagger documentation coming up]()

# FitTrack – Fitness Microservices System

FitTrack is a distributed fitness tracking application designed using microservices architecture. It allows users to register, manage profiles, choose workout plans, and track progress on various workout categories and levels. 

The system has 3 roles, ADMIN, COACH and USER.
- ADMIN has access to all endpoints and is the only one to CRUD workout categories.
- COACH is able to create an account, create workouts based on the system's categories.
- USER is able to create an account, select their preferred workout categories and workout plans associated with the categories.

---

## Microservices Overview

| Microservice       | Description                                      |
|--------------------|--------------------------------------------------|
| `user-service`     | Manages users, coaches  and their profiles.      |
| `workout-service`  | Provides workout plans and categories            |
| `progress-service` | Tracks user progress for workouts and categories |

---

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- RestTemplate (for inter-service communication)
- Maven
- Docker & Docker Compose
- Lombok
- PostgreSQL
- Makefile (for local automation)

---

## Setup

First clone it to your local machine by running

```
https://github.com/hadijahkyampeire/fitness-tracker-api-monorepo.git
cd fitness-tracker-api-monorepo
```

Then install all the necessary dependencies and build the project

```
mvn clean install
```

## Setup database

At the terminal or console type:

```
psql -U postgres
CREATE DATABASE "cs544-fit-users";
CREATE DATABASE "cs544-fit-workouts";
CREATE DATABASE "cs544-fit-workouts-progress-tracking"
```

## Starting/running the application

### Option 1: (Mac) Using Makefile (Recommended)

Make sure `make` is installed, then simply run:
```
make run
```

### Option 2: (Mac/Windows/Linux) Using Docker

Make sure `docker` and `docker compose` are installed.

then simply run these:
```
docker compose build
docker compose up
```

### Option 3: (Mac/Windows/Linux) Using IntelliJ IDE

- Open `/user-service/src/main/java/cs544/fit/user_service/UserServiceApplication.java` and run it.

- Open `/workout_progress_tracking/src/main/java/com/fit/workout_progress_tracking/WorkoutProgressTrackingApplication.java` and run it.

- Open `/workout_service/src/main/java/cs544/fit/workout_service/WorkoutServiceApplication.java`

--
## API Endpoints
You could use a GUI platform like postman to make requests to and fro the api.

###  User Service

| Method | URL                        | Description                 |
|--------|----------------------------|-----------------------------|
| POST   | `/api/auth/register-user`  | Create user with role USER  |
| POST   | `/api/auth/register-coach` | Create user with role COACH |
| POST   | `/api/auth/login`          | User login                  |
| GET    | `/api/auth/users`          | Get all users               |
| POST   | `/api/profile/coach`       | Create coach profile        |
| GET    | `/api/profile/coach/{id}`  | Get coach profile by id     |
| PUT    | `/api/profile/coach/{id}`  | Update coach profile        |
| POST   | `/api/profile/user`        | Create user profile         |
| GET    | `/api/profile/user/{id}`   | Get user profile  by id     |
| PUT    | `/api/profile/user/{id}`   | Update user profile         |

---

### Workout Service

**Workout Categories**

| Method | URL                    | Description                |
|--------|------------------------|----------------------------|
| POST   | `/api/categories`      | Create a new category      |
| GET    | `/api/categories`      | Get all workout categories | 
| GET    | `/api/categories/{id}` | Get category by ID         |
| PUT    | `/api/categories/{id}` | Update category            |
| DELETE | `/api/categories/{id}` | Delete category            |

---
**Workouts**

| Method | URL                                         | Description                                                |
|--------|---------------------------------------------|------------------------------------------------------------|
| POST   | `/api/workouts`                             | Create a new workout                                       |
| GET    | `/api/workouts`                             | Get all workout                                            |
| GET    | `/api/workouts/{id}`                        | Get workout by ID                                          |
| DELETE | `/api/workouts/{id}`                        | Delete workout                                             |
| PUT    | `/api/workouts/{id}`                        | Update workout                                             |
| GET    | `/api/workouts/level/{level}`               | Get workouts by level i.e BEGINNER, INTERMEDIATE, ADVANCED |
| GET    | `/api/workouts/user/{userId}`               | Get workouts by coach ID                                   |
| GET    | `/category/{id}`                            | Get workouts by category                                   |
| GET    | `/api/workouts/user/{userId}/level/{level}` | Get workouts by coach ID and level                         |


### 📈 Progress Service

| Method | URL                                                | Description                                      |
|--------|----------------------------------------------------|--------------------------------------------------|
| GET    | `/progress/user/{userId}`                          | Get full progress report for a user              |
| GET    | `/progress/user/{userId}/category/{categoryId}`    | Get category-level progress for a user           |
| POST   | `/progress/log`                                    | Log/update a workout status for a user           |
| GET    | `/progress/user/{userId}/workout/{workoutId}`      | Get progress for a specific workout              |

---

## Status Types Tracked

In `progress-service`, the following status values are tracked:

- `NOT_STARTED`
- `IN_PROGRESS`
- `COMPLETED`

These statuses reflect a user’s current engagement with a given workout or category.

---
