Bank Account Microservice

A complete Spring Boot microservice for managing bank accounts with REST API, GraphQL API, and Spring Data REST support.

Table of Contents

- Features
- Technologies
- Getting Started
- API Documentation
- Testing

Features

- CRUD operations for bank accounts
- Multiple API styles:
  - RESTful API with custom controllers
  - Spring Data REST with automatic endpoints
  - GraphQL API with queries and mutations
- DTO pattern for request/response handling
- Service layer for business logic
- H2 in-memory database for development
- Swagger/OpenAPI documentation
- GraphiQL interface for GraphQL testing
- Lombok for reducing boilerplate code

Technologies

- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Data REST
- Spring GraphQL
- H2 Database
- Lombok
- SpringDoc OpenAPI (Swagger)
- Maven


└─────────────────────────────────────────┘

Design Patterns Used

- DTO (Data Transfer Object): Separates internal entities from API contracts
- Repository Pattern: Abstracts data access logic
- Service Layer Pattern: Encapsulates business logic
- Dependency Injection: Constructor-based injection
- Builder Pattern: Using Lombok's @Builder for object creation

Getting Started

Prerequisites

- Java 17 or higher
- Maven 3.6+

Installation

Clone the repository:

```bash
git clone https://github.com/Nohrer/TP3_Microservice
cd TP2_Microservice
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

The application will start on http://localhost:8081

Configuration

Key configurations in application.properties:

```properties
server.port=8081
spring.datasource.url=jdbc:h2:mem:account-db
spring.h2.console.enabled=true
spring.graphql.graphiql.enabled=true
```

API Documentation

REST API Endpoints
Base URL: http://localhost:8081/api

Method	Endpoint	Description
GET	/bankAccounts	Get all bank accounts
GET	/bankAccounts/{id}	Get account by ID
POST	/bankAccounts	Create new account
PUT	/bankAccounts/{id}	Update account
DELETE	/bankAccounts/{id}	Delete account

Example Request (POST)

```json
{
  "balance": 5000.0,
  "currency": "MAD",
  "type": "CURRENT_ACCOUNT"
}
```

Example Response

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "creationDate": "2025-10-18T13:45:00.000+00:00",
  "balance": 5000.0,
  "currency": "MAD",
  "type": "CURRENT_ACCOUNT"
}
```

Spring Data REST Endpoints
Base URL: http://localhost:8081/bankAccounts

GET /bankAccounts - List all accounts
GET /bankAccounts/{id} - Get account by ID
GET /bankAccounts/search/byType?t=CURRENT_ACCOUNT - Filter by type
POST /bankAccounts - Create account
PUT /bankAccounts/{id} - Update account
DELETE /bankAccounts/{id} - Delete account

GraphQL API
Endpoint: http://localhost:8081/graphql

Queries

# Get all accounts
query {
  allBankAccounts {
    id
    balance
    currency
    type
    creationDate
  }
}

# Get account by ID
query {
  bankAccountById(id: "your-account-id") {
    id
    balance
    currency
    type
  }
}

# Filter by type
query {
  bankAccountsByType(type: "CURRENT_ACCOUNT") {
    id
    balance
    currency
  }
}

Mutations

# Create account
mutation {
  addBankAccount(bankAccount: {
    balance: 5000.0
    currency: "MAD"
    type: "SAVING_ACCOUNT"
  }) {
    id
    balance
    currency
    type
  }
}

# Update account
mutation {
  updateBankAccount(
    id: "your-account-id"
    bankAccount: {
      balance: 7500.0
      currency: "EUR"
      type: "CURRENT_ACCOUNT"
    }
  ) {
    id
    balance
    currency
  }
}

# Delete account
mutation {
  deleteBankAccount(id: "your-account-id")
}

Account Types

- CURRENT_ACCOUNT - Current/Checking account
- SAVING_ACCOUNT - Savings account

Testing

Access Testing Tools

Swagger UI: http://localhost:8081/swagger-ui.html

Interactive REST API documentation
Test endpoints directly from browser
GraphiQL: http://localhost:8081/graphiql

Interactive GraphQL IDE
Auto-completion and documentation
H2 Console: http://localhost:8081/h2-console

JDBC URL: jdbc:h2:mem:account-db
Username: sa
Password: (leave empty)

Using Postman

Import the following endpoints into Postman:

GET All Accounts

GET http://localhost:8081/api/bankAccounts
POST Create Account

POST http://localhost:8081/api/bankAccounts
Content-Type: application/json

```json
{
  "balance": 10000.0,
  "currency": "MAD",
  "type": "CURRENT_ACCOUNT"
}
```

Sample Data

The application automatically creates 9 sample accounts on startup with:

- Random account types (CURRENT_ACCOUNT or SAVING_ACCOUNT)
- Random balances between 10,000 and 100,000
Currency: MAD


Key Concepts

DTOs (Data Transfer Objects)
BankAccountRequestDTO: Used for creating/updating accounts (no ID or creation date)
BankAccountResponseDTO: Used for API responses (includes all fields)

Service Layer
Encapsulates business logic
Handles entity-DTO conversions
Provides transaction management with @Transactional

Mappers
Converts between entities and DTOs
Uses Spring's BeanUtils for property copying
Ensures separation between internal and external representations

Best Practices Implemented

- Layered Architecture - Clear separation of concerns
- DTO Pattern - Never expose entities directly
- Constructor Injection - Preferred over field injection
- Service Layer - Business logic separated from controllers
- Error Handling - Proper exception handling with meaningful messages
- Immutable IDs - IDs and creation dates cannot be modified
- RESTful Conventions - Proper HTTP methods and status codes
- API Documentation - Swagger/OpenAPI for REST, GraphiQL for GraphQL

Author
Created by Naoufal Guendouz as part of TP2 Microservice assignment
Bank Account Microservice

A complete Spring Boot microservice for managing bank accounts with REST API, GraphQL API, and Spring Data REST support.

Table of Contents

- Features
- Technologies
- Architecture
- Getting Started
- API Documentation
- Testing
- Project Structure

Features

- CRUD operations for bank accounts
- Multiple API styles:
  - RESTful API with custom controllers
  - Spring Data REST with automatic endpoints
  - GraphQL API with queries and mutations
- DTO pattern for request/response handling
- Service layer for business logic
- H2 in-memory database for development
- Swagger/OpenAPI documentation
*** End Patch

