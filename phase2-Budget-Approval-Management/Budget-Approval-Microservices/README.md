# Budget & Approval Microservices

**Developer:** Sai Charan Mekkanti
**Domain:** Finance / Workflow
**Git Branch:** feature/saicharan-budget-microservices

## 1. Overview

A microservices solution for departmental budgets and approval processing.
Budget ownership stays in `budget-service`; approval ownership stays in `approval-service`.
All external traffic is routed through a single API Gateway, and services discover
each other via Eureka.

## 2. Architecture

```
                      ┌─────────────────┐
                      │   API Gateway    │  :8080
                      └────────┬─────────┘
                               │
              ┌────────────────┴────────────────┐
              │                                  │
    ┌─────────▼─────────┐            ┌───────────▼──────────┐
    │  budget-service     │  Feign     │  approval-service     │
    │  :8081               │◄──────────┤  :8082                │
    └─────────┬───────────┘            └───────────┬──────────┘
              │                                     │
        ┌─────▼──────┐                       ┌──────▼─────┐
        │ budget_db   │                       │ approval_db │
        └────────────┘                       └────────────┘

              Both services register with:
              ┌─────────────────────┐
              │   Eureka Server      │  :8761
              └─────────────────────┘
```

- **budget-service** owns budgets, allocations, transactions, and variance calculations.
- **approval-service** owns the approve/reject workflow, and calls back into
  budget-service (via Feign, through Eureka service discovery) to update budget status.
- **API Gateway** (Spring Cloud Gateway) routes `/api/budgets/**`, `/api/approvals/**`,
  and `/api/auth/**` to the correct service.
- **Eureka Server** provides service discovery so the Gateway and Feign clients don't
  need hardcoded hostnames/ports.

## 3. Service Flow

1. `budget-service` creates a budget (`status: PENDING`)
2. `approval-service` processes the approval decision (`approve` / `reject`)
3. On approval, `approval-service` calls `budget-service` internally to flip the
   budget's status to `APPROVED` (or `REJECTED`)
4. Once approved, the budget can accept allocations/transactions

## 4. Technology Stack

- Java 21
- Spring Boot 3.x
- Spring Cloud Gateway
- Netflix Eureka (Service Discovery)
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- OpenFeign (service-to-service calls)
- Docker / Docker Compose
- Swagger / OpenAPI
- JUnit 5 + Mockito
- Maven

## 5. Required APIs

| Method | Endpoint | Service |
|---|---|---|
| POST | `/api/budgets` | budget-service |
| GET | `/api/budgets/{id}` | budget-service |
| POST | `/api/budgets/{id}/allocate` | budget-service |
| GET | `/api/budgets/{id}/variance` | budget-service |
| POST | `/api/approvals/{budgetId}/approve` | approval-service |
| POST | `/api/approvals/{budgetId}/reject` | approval-service |

Additionally:
- `POST /api/auth/login` — issues a JWT for testing (no dedicated auth-service in scope)
- `PUT /api/budgets/{id}/status` — internal endpoint, called only by approval-service via Feign

## 6. Running Locally (without Docker)

1. Create two PostgreSQL databases: `budget_db`, `approval_db`
2. Start services in this order, each in its own terminal:
   ```
   cd eureka-server && ./mvnw spring-boot:run
   cd budget-service && ./mvnw spring-boot:run
   cd approval-service && ./mvnw spring-boot:run
   cd api-gateway && ./mvnw spring-boot:run
   ```
3. Confirm all services show `UP` at `http://localhost:8761`
4. Get a token: `POST http://localhost:8080/api/auth/login`
5. Use the token as a Bearer token for all `/api/budgets/**` and `/api/approvals/**` calls

## 7. Running with Docker

From the root directory (where `docker-compose.yml` lives):

```
docker-compose up --build
```

This starts PostgreSQL, Eureka, budget-service, approval-service, and the API Gateway,
all networked together. Databases are auto-created via `init-db.sql` on first boot.

## 8. Swagger / API Docs

- budget-service: `http://localhost:8081/swagger-ui.html`
- approval-service: `http://localhost:8082/swagger-ui.html`

## 9. Running Tests

```
cd budget-service && ./mvnw test
cd approval-service && ./mvnw test
```

## 10. Security

JWT-based authentication, shared secret across both services (no separate auth-service
in this project's scope). `/api/auth/**` and internal service-to-service endpoints
(`/api/budgets/{id}/status`) are open; all other business endpoints require a valid
Bearer token.

## 11. Notes

This is a fresh, independent microservices mini-project. It does not depend on or
reuse the Phase 1 Employee Helpdesk implementation.