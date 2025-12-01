# Microservices Architecture Explanation

This document explains the key concepts and components of the microservices architecture used in this project.

## Core Concepts

### Microservices

Instead of building a single, monolithic application, we break it down into a set of smaller, independent services. Each service:

*   Is responsible for a specific business capability (e.g., billing, customer management).
*   Runs in its own process.
*   Can be developed, deployed, and scaled independently.
*   Communicates with other services over a network (typically using REST APIs or messaging).

### Advantages

*   **Scalability:** You can scale individual services based on their specific needs.
*   **Flexibility:** Each service can be written in a different programming language or use a different technology stack.
*   **Resilience:** Failure in one service doesn't necessarily bring down the entire application.
*   **Maintainability:** Smaller codebases are easier to understand and maintain.

## Key Components in this Architecture

### 1. Service Discovery (Eureka)

*   **Problem:** In a dynamic microservices environment, services need to find each other. IP addresses and ports can change frequently.
*   **Solution:** A Service Discovery server (like Netflix Eureka) acts as a phone book for your services.
    *   When a microservice starts, it registers itself with the Eureka server, providing its name, IP address, and port.
    *   When a service needs to communicate with another, it asks Eureka for the location of the target service by its name.
*   **In this project:** The `discovery-service` is the Eureka server. All other microservices are Eureka clients.

### 2. Configuration Server (Spring Cloud Config)

*   **Problem:** Managing configuration for multiple services can be challenging. You don't want to rebuild and redeploy a service just to change a configuration property.
*   **Solution:** A Configuration Server provides a centralized place to manage all configuration files.
    *   It can be backed by a Git repository, a database, or the local filesystem.
    *   Microservices connect to the Config Server on startup to fetch their configuration.
    *   Changes to the configuration can be picked up by the services without a restart (with some additional setup).
*   **In this project:** The `config-microservice` is the Spring Cloud Config server. The `config-repo` is the Git repository where the configuration files are stored.

### 3. API Gateway (Spring Cloud Gateway)

*   **Problem:** How do external clients (e.g., web browsers, mobile apps) interact with all the different microservices? Exposing every service directly can be complex and insecure.
*   **Solution:** An API Gateway acts as a single entry point for all incoming requests.
    *   It routes requests to the appropriate microservice.
    *   It can handle cross-cutting concerns like:
        *   **Authentication and Security:** Enforcing security policies in one place.
        *   **Rate Limiting:** Protecting your services from being overwhelmed.
        *   **Request/Response Transformation:** Modifying requests and responses.
*   **In this project:** The `gateway-microservice` is the Spring Cloud Gateway. It uses the Discovery Service to find the actual location of the other services.

### 4. Business Microservices

*   These are the services that implement the actual business logic of your application.
*   **Examples in this project:** `billing-microservice`, `customer-microservice`, `inventory-microservice`.
*   Each of these services will typically have:
    *   Its own database to store its data.
    *   A REST API to expose its functionality.
    *   Clients (like Spring Cloud's Feign) to communicate with other services.

## Data Management in Microservices

*   **Database per Service:** Each microservice should own its data and have its own database. This ensures loose coupling.
*   **Data Consistency:** Maintaining data consistency across services is a challenge. This is often solved using patterns like:
    *   **Sagas:** A sequence of local transactions. If one transaction fails, the saga executes compensating transactions to undo the preceding transactions.
    *   **Eventual Consistency:** Accepting that data will be consistent eventually, but not immediately.

## Routing

*   Routing is primarily handled by the **API Gateway**.
*   When a request comes to the gateway (e.g., `/api/customers/1`), the gateway looks at its routing rules.
*   The rules can be based on the path, host, headers, etc.
*   The gateway then forwards the request to the appropriate microservice (e.g., the `customer-microservice`). The gateway knows the location of the `customer-microservice` because it's registered with the **Discovery Service**.
