# Test Case Management System

## Overview

The Test Case Management System is a robust REST API built with Spring Boot that allows teams to efficiently create, track, and manage test cases. This microservice-based application provides a comprehensive solution for QA teams to organize their testing efforts with features for prioritization, status tracking, and filtering.

## Features

- **CRUD Operations**: Complete set of endpoints to create, read, update, and delete test cases
- **Pagination**: Efficient retrieval of test cases with customizable page size
- **Filtering**: Filter test cases by status and priority
- **MongoDB Integration**: Utilizes MongoDB for flexible document storage
- **Time Zone Support**: Configurable time zone for accurate timestamp tracking
- **Comprehensive Testing**: Well-tested service layer with JUnit and Mockito

## Technology Stack

- **Framework**: Spring Boot
- **Database**: MongoDB
- **Build Tool**: Maven (implied)
- **Testing**: JUnit 5, Mockito
- **Documentation**: Lombok for reducing boilerplate code
- **Architecture**: Microservice design with clear separation of concerns

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/testcases/` | Retrieve test cases with pagination and optional filtering |
| GET | `/api/testcases/{id}` | Get a specific test case by ID |
| POST | `/api/testcases/` | Create new test cases |
| PUT | `/api/testcases/{id}` | Update an existing test case |
| DELETE | `/api/testcases/{id}` | Delete a test case |

## Data Models

### TestCase Entity

Core entity representing a test case with the following attributes:

- `id`: Unique identifier (MongoDB ObjectId)
- `title`: Test case title
- `description`: Detailed description of the test case
- `status`: Current status (PENDING, IN_PROGRESS, COMPLETED, etc.)
- `priority`: Importance level (LOW, MEDIUM, HIGH, CRITICAL)
- `createdAt`: Creation timestamp
- `updatedAt`: Last update timestamp

### DTOs

The application uses Data Transfer Objects to separate API contract from internal representations:

- `TestCaseDTO`: Immutable record for transferring test case data between layers

## Setup and Configuration

### Prerequisites
- Java 8 or higher
- MongoDB
- Maven


### Project Structure

```
com.zomind.testcase/
├── Entity/
│ └── TestCase.java → Represents the TestCase entity, mapped to a database table.
├── Enums/
│ ├── Priority.java → Defines priority levels (e.g., HIGH, MEDIUM, LOW) for test cases.
│ └── Status.java → Defines possible statuses (e.g., PASSED, FAILED, IN_PROGRESS) for test cases.
├── dto/
│ └── TestCaseDTO.java → Data Transfer Object (DTO) for TestCase, used for API communication.
├── repository/
│ └── TestCaseRepositories.java → Handles database operations for TestCase using Spring Data MongoDb.
├── Service/
│ └── TestCaseService.java → Business logic layer that processes test case data.
├── controller/
│ └── TestCaseController.java → Exposes REST API endpoints for managing test cases.
└── TestcaseApplication.java → Main Spring Boot application entry point.
```

### Configuration
The application uses externalized configuration for flexibility:

```properties
# application.properties
ZonedId=    <---- for eg. "Asia/Kolkata" ----->  # Configurable time zone
spring.data.mongodb.uri= <---- MongoDb Atlas URI ----->


<h4>Let me know if you'd like any modifications! 🚀</h4>
