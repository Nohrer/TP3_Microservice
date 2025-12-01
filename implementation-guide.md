# Implementation Guide: Conference Management System

This guide provides the necessary dependencies and recommended folder structures for each microservice in the Conference Management System blueprint.

---

## 1. Parent `pom.xml` Dependencies

In your parent `pom.xml`, you should manage the versions of Spring Boot and Spring Cloud to ensure consistency across all modules.

```xml
<properties>
    <java.version>17</java.version>
    <spring-boot.version>3.1.5</spring-boot.version> <!-- Use a recent Spring Boot 3 version -->
    <spring-cloud.version>2022.0.4</spring-cloud.version> <!-- Corresponding Spring Cloud version -->
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

## 2. Core Infrastructure Services

### a. Config Server

*   **Purpose:** Centralized configuration management.
*   **Key Dependencies:**
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
    ```
*   **Main Class Annotation:** `@EnableConfigServer`

### b. Discovery Service (Eureka)

*   **Purpose:** Service registration and discovery.
*   **Key Dependencies:**
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>
    ```
*   **Main Class Annotation:** `@EnableEurekaServer`

### c. API Gateway

*   **Purpose:** Single entry point and request routing.
*   **Key Dependencies:**
    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
    ```

---

## 3. Business Microservice Structure & Dependencies

This section describes the typical structure and dependencies for your business services (User, Session, Payment).

### a. Recommended Folder Structure

Here is a standard package structure for a business microservice (using `user-service` as an example). This promotes separation of concerns.

```
user-service/
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── conference/
        │           └── userservice/
        │               ├── UserServiceApplication.java
        │               ├── config/          // For configuration classes (e.g., SecurityConfig)
        │               ├── controller/      // REST controllers (the API layer)
        │               ├── dto/             // Data Transfer Objects (for API requests/responses)
        │               ├── entity/          // JPA entities (the data model)
        │               ├── repository/      // Spring Data JPA repositories
        │               └── service/         // Business logic
        └── resources/
            ├── application.properties       // Fallback properties
            ├── bootstrap.properties         // Connects to Config Server
            └── static/
```

### b. Common Business Service Dependencies

Most of your business services will share this set of dependencies.

```xml
<dependencies>
    <!-- For creating REST APIs -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- For connecting to the Config Server -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>

    <!-- For registering with the Discovery Service -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

    <!-- For management endpoints (/actuator/health, etc.) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- For database interaction -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!-- Or use another database driver like: -->
    <!--
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    -->

    <!-- Optional: For reducing boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### c. Service-Specific Dependencies

Add these in addition to the common dependencies above.

*   **User Service:**
    *   Needs security for authentication and authorization.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <!-- If you use JWTs for tokens, you'll need a JWT library -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    ```

*   **Session Service & Payment Service:**
    *   Need a Feign client to communicate with other services.
    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    ```
    *   In your main application class, add the `@EnableFeignClients` annotation.

*   **Notification Service:**
    *   This service is simpler. It doesn't need `spring-boot-starter-data-jpa` if it's stateless.
    *   It needs the mail starter.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    ```
