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

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/orders` | Create an order |
| GET | `/api/orders/{orderId}` | Retrieve an order |
| GET | `/api/orders` | Retrieve all orders |

### Current Limitations

- Inventory availability is currently simulated.
- Order processing is asynchronous within the Order Service.
- No message broker or inter-service communication has been implemented yet.
- Automated tests are planned for a later stage.

### Next Development Steps

- Build a separate Inventory Microservice.
- Integrate Order Service with Inventory Service.
- Introduce RabbitMQ for asynchronous messaging.
- Build Notification Service.
- Containerize services using Docker.