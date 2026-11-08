# Order Processing & Notification Platform

A Java-based learning and portfolio project demonstrating multithreading, concurrent task execution, Spring Boot microservices, and asynchronous event-driven processing.

The project is being developed incrementally, starting with Java concurrency fundamentals and progressing towards a distributed order-processing platform.

## Current Progress

### 1. Java Concurrency Lab

- Created and started Java threads using `Runnable`.
- Explored `Thread.start()` versus direct method invocation.
- Executed order-processing tasks using `ExecutorService`.
- Used a fixed-size thread pool to process multiple orders.
- Explored `Callable` and `Future` for asynchronous task execution.
- Practiced `CompletableFuture` for asynchronous and combined operations.
- Explored exception handling in asynchronous tasks.
- Used concurrent execution techniques for order-processing scenarios.

### 2. Order Service — Initial Version

Implemented a Spring Boot REST API for basic order management.

**Features**

- Create orders using `POST /api/orders`.
- Retrieve an order using `GET /api/orders/{orderId}`.
- List all orders using `GET /api/orders`.
- Validate incoming requests using Jakarta Bean Validation.
- Return appropriate HTTP status codes for successful creation, invalid input, and missing orders.
- Use constructor injection to connect the controller and service layers.
- Use `ConcurrentHashMap` for thread-safe in-memory order storage.
- Use `AtomicLong` for thread-safe order ID generation.

**Current Limitations**

- Orders are stored in memory and are lost when the application restarts.
- Inventory validation and stock reservation are not implemented yet.
- Database persistence and automated API tests are planned.

## Planned Features

- Inventory Service with concurrency-safe stock reservation.
- Notification Service.
- RabbitMQ-based asynchronous event processing.
- Communication between independent microservices.
- Failure handling, retries, and duplicate-message protection.
- MySQL persistence.
- JUnit testing.
- Docker and Docker Compose for service deployment.

## Technology Stack

| Technology              | Purpose                         |
| ----------------------- | ------------------------------- |
| Java 21                 | Core programming language       |
| Maven                   | Build and dependency management |
| Spring Boot             | REST APIs and microservices     |
| Spring Web              | REST API development            |
| Jakarta Bean Validation | Request validation              |
| ConcurrentHashMap       | Thread-safe in-memory storage   |
| AtomicLong              | Thread-safe ID generation       |
| MySQL                   | Planned database persistence    |
| RabbitMQ                | Planned asynchronous messaging  |
| JUnit                   | Planned automated testing       |
| Docker                  | Planned containerization        |

## Project Structure

```text
order-processing-platform/
│
├── src/
│   └── main/java/com/learning/concurrency/
│       └── Java concurrency examples
│
└── order-service/
    ├── src/
    │   ├── main/
    │   │   ├── java/com/learning/orderservice/
    │   │   └── resources/
    │   └── test/
    └── pom.xml
```

## Running the Project

### Java Concurrency Lab

Open the project in IntelliJ IDEA and run the required Java classes, such as:

- `com.learning.concurrency.OrderProcessingDemo`
- `com.learning.concurrency.OrderProcessingWithThreadPool`
- `com.learning.concurrency.CallableFutureDemo`

### Order Service

1. Open the `order-service` directory as a Maven project in IntelliJ IDEA.
2. Wait for Maven dependencies to download.
3. Run `OrderServiceApplication`.

Default application URL:

`http://localhost:8080`

## Order Service API Endpoints

| Method | Endpoint                | Purpose           |
| ------ | ----------------------- | ----------------- |
| POST   | `/api/orders`           | Create an order   |
| GET    | `/api/orders/{orderId}` | Retrieve an order |
| GET    | `/api/orders`           | List all orders   |

## Project Status

**In Development**

The project is being built incrementally, starting with Java concurrency fundamentals and a basic Spring Boot Order Service. Inventory management, notification processing, asynchronous messaging, database integration, and containerization are planned for upcoming phases.
