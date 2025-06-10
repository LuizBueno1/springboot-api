# Anime Management API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Security](https://img.shields.io/badge/Spring%20Security-Enabled-blue.svg)](https://spring.io/projects/spring-security)
[![API](https://img.shields.io/badge/REST%20API-Full%20CRUD-success.svg)](https://restfulapi.net/)
[![Documentation](https://img.shields.io/badge/Swagger-OpenAPI%203-85EA2D.svg)](https://swagger.io/)

A RESTful API built with Spring Boot for managing anime collections. Features comprehensive CRUD operations, role-based authentication, input validation, and extensive error handling.

## Features

### Core Functionality
- Complete CRUD Operations - Create, Read, Update, Delete anime entries
- Pagination Support - Efficient data retrieval with customizable page sizes
- Advanced Search - Find anime by name with flexible search capabilities
- Role-Based Security - Multi-level authentication and authorization
- Input Validation - Comprehensive request validation with detailed error messages
- API Documentation - Interactive Swagger/OpenAPI documentation

### Security Features
- Spring Security Integration - Industry-standard security framework
- Dual Authentication System - In-memory and database user management
- Role-Based Access Control - USER and ADMIN role hierarchies
- Multiple Auth Methods - Basic Auth and Form-based authentication
- Method-Level Security - Fine-grained endpoint protection

### Technical Excellence
- Clean Architecture - Well-structured, maintainable codebase
- JPA/Hibernate Integration - Efficient database operations
- MapStruct Mapping - Type-safe object mapping
- Custom Exception Handling - Detailed error responses
- Actuator Integration - Health checks and monitoring endpoints

### Prerequisites
- Java 17 or higher
- PostgreSQL/MySQL (or H2 for development)
- Maven 3.6+

## Authentication

The API supports dual authentication modes:

### In-Memory Users (Development)
| Username | Password | Roles |
|----------|----------|-------|
| william2 | academy | USER, ADMIN |
| devdojo2 | academy | USER |

### Database Users (Production)
Users stored in `DevDojoUser` entity with encrypted passwords and custom authorities.

## API Endpoints

### Anime Management

#### Public Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/animes` | List all anime (paginated) | USER |
| `GET` | `/animes/all` | List all anime (non-paginated) | USER |
| `GET` | `/animes/{id}` | Get anime by ID | USER |
| `GET` | `/animes/find?name={name}` | Search anime by name | USER |

#### Protected Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/animes/by-id/{id}` | Get anime by ID (Admin only) | ADMIN |
| `POST` | `/animes` | Create new anime | USER |
| `PUT` | `/animes` | Update existing anime | USER |
| `DELETE` | `/animes/admin/{id}` | Delete anime | ADMIN |

### System Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `GET` | `/actuator/**` | Application health & metrics | Public |

## Request/Response Examples

### Create Anime
```http
POST /animes
Content-Type: application/json
Authorization: Basic <credentials>

{
    "name": "Attack on Titan"
}
```

**Response:**
```json
{
    "id": 1,
    "name": "Attack on Titan"
}
```

### Get Paginated Anime List
```http
GET /animes?page=0&size=10&sort=name,asc
Authorization: Basic <credentials>
```

**Response:**
```json
{
    "content": [
        {
            "id": 1,
            "name": "Attack on Titan"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10
    },
    "totalElements": 1,
    "totalPages": 1,
    "first": true,
    "last": true
}
```

### Update Anime
```http
PUT /animes
Content-Type: application/json
Authorization: Basic <credentials>

{
    "id": 1,
    "name": "Attack on Titan: Final Season"
}
```

### Search Anime
```http
GET /animes/find?name=Attack
Authorization: Basic <credentials>
```

## Error Handling

The API provides comprehensive error responses:

### Validation Error
```json
{
    "title": "Bad Request Exception, Invalid Fields",
    "status": 400,
    "details": "Check the field(s) error",
    "developerMessage": "MethodArgumentNotValidException",
    "timestamp": "2024-01-15T10:30:00",
    "fields": "name",
    "fieldsMessage": "The anime name cannot be empty"
}
```

### Not Found Error
```json
{
    "title": "Bad Request Exception, Check the Documentation",
    "status": 400,
    "details": "Anime not found",
    "developerMessage": "BadRequestException",
    "timestamp": "2024-01-15T10:30:00"
}
```

## Architecture

### Project Structure
```
com.example.springboot_startspringio/
├── client/          # REST client examples
├── config/          # Security and web configuration
├── controller/      # REST controllers
├── domain/          # Entity classes
├── exception/       # Custom exceptions and handlers
├── mapper/          # MapStruct mappers
├── repository/      # JPA repositories
├── requests/        # Request DTOs
├── service/         # Business logic
└── util/           # Utility classes
```

### Key Components
- **AnimeController**: REST endpoints for anime operations
- **AnimeService**: Business logic and transaction management
- **SecurityConfig**: Authentication and authorization setup
- **RestExceptionHandler**: Global exception handling
- **AnimeMapper**: Object mapping with MapStruct

## Configuration

### Default Pagination
- Default page size: 5 items
- Configurable via `DevDojoWebMvcConfigurer`

### Security Configuration
- CSRF disabled for API usage
- Role-based endpoint protection
- Support for both form login and HTTP Basic Auth

## Testing

The project features a comprehensive test suite covering all layers of the application with complete coverage of critical business logic and API endpoints.

### Test Architecture

#### Test Structure
- **Unit Tests** - Service and controller layer testing with mocked dependencies
- **Integration Tests** - Full application context testing with real database
- **Repository Tests** - JPA repository testing with `@DataJpaTest`
- **Test Utilities** - Reusable test data creators and helper classes

#### Test Coverage

| Layer | Test Class | Coverage |
|-------|------------|----------|
| **Controller** | `AnimeControllerTest` | All endpoints (CRUD operations) |
| **Service** | `AnimeServiceTest` | Business logic and exception handling |
| **Repository** | `AnimeRepositoryTest` | JPA operations and constraints |
| **Integration** | `AnimeControllerIT` | End-to-end API testing |

### Unit Tests

#### Controller Tests (`AnimeControllerTest`)
- **Scope**: Tests REST controller endpoints with mocked service layer
- **Framework**: JUnit 5 + Mockito + AssertJ
- **Coverage**: 
  - GET `/animes` - Paginated listing
  - GET `/animes/all` - Non-paginated listing  
  - GET `/animes/{id}` - Find by ID
  - GET `/animes/find` - Search by name
  - POST `/animes` - Create anime
  - PUT `/animes` - Update anime
  - DELETE `/animes/admin/{id}` - Delete anime
  - Empty result handling

#### Service Tests (`AnimeServiceTest`)
- **Scope**: Tests business logic with mocked repository layer
- **Framework**: JUnit 5 + Mockito + AssertJ
- **Coverage**:
  - Data retrieval operations
  - CRUD operations
  - Exception handling (`BadRequestException`)
  - Edge cases (empty results, not found scenarios)

#### Repository Tests (`AnimeRepositoryTest`)
- **Scope**: Tests JPA operations against embedded H2 database
- **Framework**: `@DataJpaTest` + JUnit 5 + AssertJ
- **Coverage**:
  - Entity persistence and updates
  - Custom query methods (`findByName`)
  - Constraint validation
  - Delete operations

### Integration Tests

#### End-to-End Tests (`AnimeControllerIT`)
- **Scope**: Full application testing with real HTTP requests
- **Framework**: `@SpringBootTest` + `TestRestTemplate`
- **Features**:
  - **Multi-Role Authentication Testing**
    - USER role authentication (`testRestTemplateRoleUser`)
    - ADMIN role authentication (`testRestTemplateRoleAdmin`)
  - **Real Database Operations** - Uses `@AutoConfigureTestDatabase`
  - **Fresh Test Context** - `@DirtiesContext` ensures isolated tests
  - **Security Testing** - Validates role-based access control

### Test Utilities

#### Test Data Creators
- **`AnimeCreator`** - Creates test anime entities
  - `createAnimeToBeSaved()` - New anime without ID
  - `createValidAnime()` - Complete anime with ID
  - `createValidUpdatedAnime()` - Modified anime for updates

- **`AnimePostRequestBodyCreator`** - Creates POST request DTOs
- **`AnimePutRequestBodyCreator`** - Creates PUT request DTOs

### Test Validation Features

- Authentication Testing - Both USER and ADMIN role validation
- Security Enforcement - Forbidden access scenarios (403 responses)
- Data Validation - Constraint violation handling
- Error Scenarios - Not found exceptions and empty results
- HTTP Status Validation - Proper response codes (200, 201, 204, 403)
- Response Structure - Complete DTO validation

### Example Test Output
```bash
[INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] AnimeControllerTest .................... SUCCESS
[INFO] AnimeServiceTest ....................... SUCCESS  
[INFO] AnimeRepositoryTest .................... SUCCESS
[INFO] AnimeControllerIT ...................... SUCCESS
```

The comprehensive test suite ensures reliability, maintainability, and confidence in API behavior across all scenarios.

## Documentation

- **Swagger/OpenAPI**: Available at `/swagger-ui.html`
- **Actuator Endpoints**: Available at `/actuator`
- **API Annotations**: Comprehensive OpenAPI annotations for all endpoints

---
