
# Purchase Requistion Microservices

A backend Purchase Requisition Management System built using Spring Boot and Microservices architecture.

The application manages the complete purchase requisition workflow, including purchase creation, approval, rejection, service discovery, API Gateway routing, JWT authentication, RBAC authorization, validation, exception handling, Swagger documentation, PostgreSQL persistence, unit testing, and Docker containerization.

---

## Architecture

The application follows a microservices-based architecture.


                         ┌─────────────────────┐
                         │     Client / UI     │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     API Gateway     │
                         │       :8080         │
                         │ JWT + RBAC Security │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┴───────────────┐
                    │                               │
                    ▼                               ▼
          ┌──────────────────┐           ┌──────────────────┐
          │ Purchase Service │           │ Approval Service │
          │      :8081       │           │      :8082       │
          └────────┬─────────┘           └────────┬─────────┘
                   │                              │
                   │                              │
                   └──────────────┬───────────────┘
                                  │
                                  ▼
                       ┌─────────────────────┐
                       │   Service Registry  │
                       │       Eureka        │
                       │       :8761         │
                       └─────────────────────┘

                       ┌─────────────────────┐
                       │     PostgreSQL      │
                       │       :5432         │
                       └─────────────────────┘


# Microservices
| Service          | Port | Responsibility                               |
| ---------------- | ---: | -------------------------------------------- |
| API Gateway      | 8080 | Request routing, JWT authentication and RBAC |
| Purchase Service | 8081 | Purchase requisition management              |
| Approval Service | 8082 | Purchase approval and rejection workflow     |
| Service Registry | 8761 | Eureka service discovery                     |
| PostgreSQL       | 5432 | Persistent data storage                      |

# Technologies
Java 21
Spring Boot
Spring Cloud Gateway
Spring Cloud Netflix Eureka
Spring Data JPA
Spring Security
JWT Authentication
Role-Based Access Control (RBAC)
PostgreSQL
Maven
Swagger / OpenAPI
Jakarta Bean Validation
Global Exception Handling
JUnit
Mockito
Docker
Docker Compose

# Features
## Purchase Requisition Management

The Purchase Service provides APIs to:

Create a purchase requisition
Get all purchase requisitions
Get a purchase requisition by ID
Update purchase status
Calculate purchase item totals
Maintain purchase request status

Default purchase status: PENDING

## Approval Management

The Approval Service provides APIs to:

Approve a purchase requisition
Reject a purchase requisition
Store manager information
Store approval/rejection comments
Maintain approval status
Prevent duplicate approval
Prevent duplicate rejection
Automatically update the Purchase Service status

Approval statuses:
    APPROVED
    REJECTED

# Security

The application uses JWT-based authentication through the API Gateway.

The API Gateway is responsible for:

Validating JWT tokens
Extracting user roles
Applying role-based authorization
Routing requests to downstream services

Example roles:

ADMIN
MANAGER
EMPLOYEE

Authentication and authorization are handled at the gateway layer so that downstream services can focus on their business responsibilities.

# Service Discovery

Netflix Eureka is used for service discovery.

Eureka Server:

http://localhost:8761

The following services register with Eureka:

purchase-service
approval-service
api-gateway

# Database

PostgreSQL is used as the primary database.

Default PostgreSQL configuration:

Host: localhost
Port: 5432
Username: postgres
Password: your password

## Databases:

purchase_db
approval_db

The database initialization scripts are located under:

postgres/init
API Endpoints
Purchase Service

# Base URL:
/api/purchases
## Create Purchase
POST /api/purchases
Get All Purchases
GET /api/purchases
Get Purchase By ID
GET /api/purchases/{id}
## Update Purchase Status
PATCH /api/purchases/{id}/status
## Approval Service
Approval APIs are exposed through the API Gateway.
Typical operations:
POST /api/approvals/{purchaseId}/approve
POST /api/approvals/{purchaseId}/reject

The Approval Service communicates with the Purchase Service to update the corresponding purchase status.

# Validation

Jakarta Bean Validation is used for request validation.

Examples include:

Required fields
Valid employee information
Valid purchase item information
Valid quantities
Valid prices
Valid approval request data
Valid status update requests

Invalid requests are handled through the application's global exception handling mechanism.

# Global Exception Handling

The application contains centralized exception handling for consistent API error responses.

The exception handling mechanism provides:

HTTP status
Error message
Timestamp
Request path
Validation errors where applicable

This avoids duplicating exception-handling logic across individual controllers.

# Swagger / OpenAPI

Swagger UI is available for API documentation.

Purchase Service:

http://localhost:8081/swagger-ui.html

Approval Service:

http://localhost:8082/swagger-ui.html

API Gateway:

http://localhost:8080

Swagger can be used to test the REST APIs during development.

# Unit Testing

Unit tests are implemented for the important service/business logic.

Testing technologies:

JUnit
Mockito
Spring Boot Test

The tests cover important scenarios such as:

Purchase creation
Purchase retrieval
Purchase status update
Purchase not found
Approval workflow
Rejection workflow
Duplicate approval
Duplicate rejection
Repository interaction
External service interaction

Tests can be executed using Maven.

mvn test

# Docker

The project is containerized using Docker.

The Docker environment contains:

API Gateway
Purchase Service
Approval Service
Eureka Service Registry
PostgreSQL

Docker Compose is used to start the complete application environment.

# Docker Project Structure

            purchase-req-ms
            │
            ├── docker-compose.yml
            │
            ├── postgres
            │   └── init
            │
            ├── api-gateway
            │   └── api-gateway
            │       └── api-gateway
            │           └── Dockerfile
            │
            ├── purchase-service
            │   └── purchase-service
            │       └── purchase-service
            │           └── Dockerfile
            │
            ├── approval-service
            │   └── approval-service
            │       └── approval-service
            │           └── Dockerfile
            │
            └── service-registry
                └── service-registry
                    └── service-registry
                        └── Dockerfile

# Running With Docker

Navigate to the project root:cd purchase-req-ms

Validate the Docker Compose configuration:docker compose config

Build the application images:docker compose build

Start all services:docker compose up -d

Check running containers:docker compose ps

View logs:docker compose logs

View logs for an individual service:
docker compose logs purchase-service
docker compose logs approval-service

Stop the application: docker compose down

# Running Locally Without Docker

Start PostgreSQL first and create the required databases:

purchase_db
approval_db

Start the services in the following order:

1. Service Registry
Port: 8761
2. Purchase Service
Port: 8081
3. Approval Service
Port: 8082
4. API Gateway
Port: 8080
# Maven Commands

Build the project:mvn clean package

Run tests:mvn test

Build without running tests:mvn clean package -DskipTests

# Project Structure

            purchase-req-ms
            │
            ├── api-gateway
            │   └── api-gateway
            │       └── api-gateway
            │
            ├── approval-service
            │   └── approval-service
            │       └── approval-service
            │
            ├── purchase-service
            │   └── purchase-service
            │       └── purchase-service
            │
            ├── service-registry
            │   └── service-registry
            │       └── service-registry
            │
            ├── postgres
            │   └── init
            │
            ├── docker-compose.yml
            │
            └── README.md

# Purchase Workflow

            Employee
            │
            ▼
            Create Purchase
            │
            ▼
            PENDING
            │
            ├───────────────┐
            │               │
            ▼               ▼
            Approve          Reject
            │               │
            ▼               ▼
            APPROVED         REJECTED

When a purchase is approved or rejected, the Approval Service communicates with the Purchase Service and updates the purchase status.

# Error Handling

The application handles common errors including:

Resource not found
Validation failure
Invalid request
Duplicate approval
Duplicate rejection
Unauthorized access
Forbidden access
Internal server errors

# Configuration
Application configuration is maintained in each microservice's:
src/main/resources/application.properties
Docker-specific configuration is supplied through environment variables in:
docker-compose.yml

# Branch
Development branch: feature/kirubakaran-purchase-microservices

# Author
## Kirubakaran S
Java Developer

# License

This project is developed for learning, development and demonstration purposes.