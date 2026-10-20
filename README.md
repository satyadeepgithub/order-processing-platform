# Order Processing & Notification Platform

A Java learning and portfolio project exploring multithreading, concurrent task execution, Spring Boot, microservices, and asynchronous event-driven processing.

## Current Progress

### Java Concurrency Lab
- Created and started Java threads using `Runnable`.
- Explored `Thread.start()` versus direct method invocation.
- Executed order-processing tasks using `ExecutorService`.
- Used a fixed-size thread pool to process multiple orders.
- Observed concurrent execution and thread reuse.

## Planned Features
- Order Service using Spring Boot and REST APIs
- Inventory Service with concurrency-safe stock reservation
- Notification Service
- RabbitMQ-based asynchronous event processing
- Failure handling, retries, and duplicate-message protection
- MySQL persistence
- JUnit testing and Docker Compose

## Technology Stack
- Java 21
- Maven
- Spring Boot (planned)
- MySQL (planned)
- RabbitMQ (planned)
- Docker (planned)

## Run the Java Concurrency Lab

Open the project in IntelliJ IDEA and run either:

- `com.learning.concurrency.OrderProcessingDemo`
- `com.learning.concurrency.OrderProcessingWithThreadPool`

## Project Status

In development. Features will be added incrementally as part of a hands-on Java concurrency and microservices learning project.