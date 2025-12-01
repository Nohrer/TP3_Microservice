# Blueprint: Conference Management System

This document outlines the microservices architecture for a Conference Management System.

## 1. Core Infrastructure

We will use the same core infrastructure services as the reference project:

*   **Config Server:** For centralized configuration.
*   **Discovery Service (Eureka):** For service registration and discovery.
*   **API Gateway:** As the single entry point for all client requests.

---

## 2. Business Microservices

Here is the proposed breakdown of business microservices for the conference management system.

### a. User Service

*   **Responsibilities:** Handles all aspects of user management, including authentication and authorization.
*   **Entities:**
    *   `User` (id, firstName, lastName, email, password, roles)
*   **Example API Endpoints:**
    *   `POST /auth/register`: Register a new user.
    *   `POST /auth/login`: Authenticate a user and issue a token (e.g., JWT).
    *   `GET /users/me`: Get the profile of the currently logged-in user.
    *   `GET /users/{id}`: Get public information about a user.
*   **Database:** A dedicated database with a `users` table.

### b. Session Service

*   **Responsibilities:** Manages all information related to conference sessions, speakers, and the schedule.
*   **Entities:**
    *   `Session` (id, title, description, startTime, endTime, room, speakerId)
    *   `Speaker` (id, name, bio, photoUrl, userId)
*   **Example API Endpoints:**
    *   `GET /sessions`: Get a list of all sessions.
    *   `GET /sessions/{id}`: Get details for a specific session.
    *   `POST /sessions`: (Admin) Create a new session.
    *   `GET /speakers`: Get a list of all speakers.
*   **Database:** A dedicated database with `sessions` and `speakers` tables.
*   **Inter-service Communication:**
    *   May call the **User Service** to link a `Speaker` to a `User` account.

### c. Payment & Ticketing Service

*   **Responsibilities:** Handles ticket creation, pricing, purchasing, and payment integration.
*   **Entities:**
    *   `TicketType` (id, name, price, conferenceId)
    *   `Ticket` (id, ticketCode, userId, ticketTypeId, status: [PAID, CANCELED])
    *   `Payment` (id, ticketId, amount, timestamp, paymentGatewayTransactionId)
*   **Example API Endpoints:**
    *   `GET /tickets/types`: Get available ticket types and prices.
    *   `POST /tickets/purchase`: Initiate the purchase of a ticket. This would be a multi-step process involving a payment gateway.
    *   `GET /users/{userId}/tickets`: Get all tickets for a specific user.
*   **Database:** A dedicated database for ticketing and payment information.
*   **Inter-service Communication:**
    *   Calls the **User Service** to validate the `userId`.
    *   Calls the **Notification Service** to send a confirmation email after a successful purchase.

### d. Notification Service

*   **Responsibilities:** A generic service for sending notifications, primarily emails.
*   **Entities:** This service might not have its own persistent entities, or it could have a `NotificationLog`.
*   **Example API Endpoints:**
    *   `POST /notifications/email`: Send an email.
        *   **Request Body:** `{ "to": "user@example.com", "subject": "Your Ticket", "body": "..." }`
*   **Database:** Optional, for logging purposes.
*   **Inter-service Communication:** This service is called by other services but does not initiate communication itself.

---

## 3. API Gateway Routing

The API Gateway will route requests to the appropriate service. The `lb://` prefix indicates that the gateway will look up the service in the Discovery Service.

```yaml
# Example configuration for the API Gateway
spring:
  cloud:
    gateway:
      routes:
        # User Service routes
        - id: user-service
          uri: lb://USER-SERVICE
          predicates:
            - Path=/auth/**, /users/**

        # Session Service routes
        - id: session-service
          uri: lb://SESSION-SERVICE
          predicates:
            - Path=/sessions/**, /speakers/**

        # Payment Service routes
        - id: payment-service
          uri: lb://PAYMENT-SERVICE
          predicates:
            - Path=/tickets/**

        # Notification Service routes (optional, might be internal-only)
        - id: notification-service
          uri: lb://NOTIFICATION-SERVICE
          predicates:
            - Path=/notifications/**
```

---

## 4. Example Workflow: User Buys a Ticket

1.  **Client -> API Gateway:** `POST /tickets/purchase` with user token and ticket details.
2.  **API Gateway -> Payment Service:** Forwards the request to `lb://PAYMENT-SERVICE/tickets/purchase`.
3.  **Payment Service:**
    a.  Receives the request.
    b.  (Optional) Calls **User Service** (`GET /users/me`) to validate the user token and get user details.
    c.  Processes the payment with an external payment provider (e.g., Stripe, PayPal).
    d.  If payment is successful, it creates a `Ticket` record in its own database.
    e.  Makes an asynchronous call to the **Notification Service** (`POST /notifications/email`) to send a confirmation email with the ticket.
4.  **Payment Service -> Client:** Returns a success response.
