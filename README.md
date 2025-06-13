# Budgeting Application

A Spring Boot application for managing personal finances and budgeting.

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Getting Started

1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## Features

- RESTful API endpoints
- H2 in-memory database
- JPA for data persistence
- Input validation
- Lombok for reducing boilerplate code

## H2 Database Console

The H2 database console is available at `http://localhost:8080/h2-console`

Database connection details:
- JDBC URL: `jdbc:h2:mem:budgetdb`
- Username: `sa`
- Password: `password`

## Project Structure

```
src/main/java/com/budgetingapp/
├── BudgetingAppApplication.java
├── controller/
├── service/
├── repository/
├── model/
└── config/
```

## API Documentation

API documentation will be available at `http://localhost:8080/swagger-ui.html` once implemented. 