## Order Service — Spring Boot

Implemented the initial Order Service using Java 21 and Spring Boot.

### Features

- Create orders through REST APIs
- Retrieve an order by ID
- Retrieve all orders
- Request validation using Jakarta Bean Validation
- Centralized exception handling using `@RestControllerAdvice`
- MySQL persistence using Spring Data JPA
- Asynchronous order processing using Spring `@Async`
- Configured worker thread pool using `ThreadPoolTaskExecutor`
- Background processing using `CompletableFuture`
- Order status updates: `PENDING`, `CONFIRMED`, `REJECTED`

### Technology Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Maven
- CompletableFuture and ExecutorService

### API Endpoints

| Method | Endpoint                | Description         |
| ------ | ----------------------- | ------------------- |
| POST   | `/api/orders`           | Create an order     |
| GET    | `/api/orders/{orderId}` | Retrieve an order   |
| GET    | `/api/orders`           | Retrieve all orders |

## Inventory Service — Spring Boot

Implemented a separate Inventory Microservice using Java 21 and Spring Boot.

### Features

- Add new products to inventory
- Retrieve product details by product code
- Retrieve all products
- Reserve product stock for orders
- Prevent stock from becoming negative
- Handle duplicate reservation requests using order ID
- Prevent concurrent stock overselling using pessimistic database locking
- Maintain reservation history

### APIs

| Method | Endpoint                                        | Description           |
| ------ | ----------------------------------------------- | --------------------- |
| POST   | `/api/inventory/products`                       | Add a product         |
| GET    | `/api/inventory/products`                       | Get all products      |
| GET    | `/api/inventory/products/{productCode}`         | Get product details   |
| POST   | `/api/inventory/products/{productCode}/reserve` | Reserve product stock |

### Database

- Database: `inventory_db`
- Tables: `product_inventory`, `inventory_reservations`

### Technology

Java 21, Spring Boot, Spring Web, Spring Data JPA, Hibernate, MySQL, Maven.

### Current Microservices

| Service           | Port | Database     |
| ----------------- | ---: | ------------ |
| Order Service     | 8080 | order_db     |
| Inventory Service | 8081 | inventory_db |

### Upcoming Enhancements

- Integrate Order Service with Inventory Service using REST communication
- Implement inter-service communication using Spring RestClient
- Introduce Notification Service
- Integrate RabbitMQ for asynchronous messaging
- Dockerize microservices
- Implement distributed transaction handling using the Saga pattern
