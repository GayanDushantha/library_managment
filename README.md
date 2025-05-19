# 📚 Library Management System

This is a Spring Boot backend application for managing a library system. It supports managing books and borrowers, and provides a set of RESTful APIs to interact with the system.

---

## ✨ Features

- Register new borrowers and books  
- View all available books in the library  
- Borrow and return books (with validation to ensure a book is borrowed only once at a time)  
- Multiple books with the same ISBN can be added (each tracked by a unique book ID)  
- Input validation and error handling on all APIs  
- Swagger UI for API documentation  
- Multiple environment support: development, production  
- Containerized setup using Docker and Docker Compose  
- Flyway integration for database migration on application startup  

---

## 🧪 API Endpoints

### 📥 Registration

- `POST /api/v1/borrowers` – Register a new borrower  
- `POST /api/v1/books` – Register new books  

### 📚 Library Access

- `GET /api/v1/books` – Get all books in the library  

### 🔄 Borrowing Actions

- `POST /api/v1/borrow` – Borrow a book by its unique ID  
- `POST /api/v1/return` – Return a previously borrowed book  

> Full API documentation available via Swagger UI.

---

## 🚀 Getting Started

### Option 1: Run with Docker (Recommended)

Ensure you have Docker and Docker Compose installed.

```bash
# Build the project to generate the JAR file
mvn clean package

# Start application and dependencies using Docker Compose
docker-compose up -d
```

- This command will:
  - Build the project and generate the JAR file inside the `target/` directory
  - Spin up a PostgreSQL database container
  - Launch the Spring Boot application container using the generated JAR
- The application will be accessible at: `http://localhost:8080`

### Option 2: Run without Docker

1. Ensure the following are installed:
   - Java (version 17+)
   - PostgreSQL

2. Create a PostgreSQL database (e.g., `library_db`).

3. Update your database credentials in `src/main/resources/application-dev.properties`:

```properties
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
```

4. Run the application:

```bash
./mvnw spring-boot:run
```

- Flyway will automatically create required tables and insert sample data on startup.

---

## 🧾 API Documentation

Once the application is running, access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

This provides detailed documentation for all available endpoints and their inputs.

---

## 🧱 Tech Stack

- **Backend:** Java 17, Spring Boot  
- **Database:** PostgreSQL  
- **Containerization:** Docker, Docker Compose  
- **Migration Tool:** Flyway  
- **API Docs:** Swagger / OpenAPI  

---

## 💡 Why PostgreSQL?

PostgreSQL was chosen for the following reasons:

- **Performance:** Handles large datasets and concurrent connections efficiently  
- **Stability:** Proven track record in production environments  
- **Advanced Features:** Supports JSON, full-text search, and complex queries  
- **Strong community and support:** Extensive documentation and regular updates  
- **Compatibility:** Seamlessly integrates with Spring Boot and Flyway  
- **Open Source:** Completely free with no licensing fees

---

## 📂 Project Structure

```
library-management/
├── src/
│   └── main/
│       ├── java/...
│       └── resources/
│           ├── application-dev.properties
│           ├── application-prod.properties
│           └── ...
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```
