# Warehouse Inventory Microservices

Java 21 + Spring Boot 3.5.6 + Spring Cloud + Eureka + API Gateway + PostgreSQL.

## Services

| Service | Port | Purpose |
|---|---:|---|
| Eureka Server | 8761 | Service discovery |
| API Gateway | 8080 | Single entry point |
| Warehouse Service | 8081 | Warehouse + inventory |
| Transfer Service | 8082 | Transfer lifecycle |

## Databases

- `warehouse_db` contains `warehouse` and `inventory`
- `transfer_db` contains `transfer_entity`

## Business flow

1. `POST /api/transfers` checks stock using warehouse-service through Eureka/OpenFeign.
2. If stock is available, transfer is created as `PENDING`.
3. `POST /api/transfers/{id}/approve` changes status to `APPROVED`.
4. `POST /api/transfers/{id}/complete` checks stock again.
5. warehouse-service decreases source quantity and increases/creates destination quantity.
6. transfer becomes `COMPLETED`.

## Existing sample data

Warehouse:
- 1 = Chennai
- 2 = Bangalore

Inventory:
- id 1 = Dell Laptop, quantity 20, warehouse 1
- id 2 = HP Laptop, quantity 50, warehouse 2
- id 3 = Lenovo Laptop, quantity 50, warehouse 1

## PostgreSQL

If PostgreSQL is running locally, create:

```sql
CREATE DATABASE warehouse_db;
CREATE DATABASE transfer_db;
```

The application properties use:
- username: `postgres`
- password: `Admin@123`

Change them if your PostgreSQL credentials differ.

## Run order

Start:
1. Eureka Server
2. Warehouse Service
3. Transfer Service
4. API Gateway

Then open:
`http://localhost:8761`

## Test through API Gateway

### Check stock
GET
`http://localhost:8080/api/inventory/check?warehouseId=1&productId=1&quantity=5`

### Create transfer
POST
`http://localhost:8080/api/transfers`

Body:
```json
{
  "sourceWarehouseId": 1,
  "destinationWarehouseId": 2,
  "productId": 1,
  "quantity": 5
}
```

### Approve
POST
`http://localhost:8080/api/transfers/1/approve`

### Complete
POST
`http://localhost:8080/api/transfers/1/complete`

After completion, Dell Laptop quantity at Chennai becomes 15 and a destination inventory record for Bangalore is created/increased by 5.

## Maven

From the project root:

```bash
mvn clean install
```

Each service can also be run from its directory:

```bash
mvn spring-boot:run
```
