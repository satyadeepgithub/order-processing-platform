package com.learning.orderservice.service;

import com.learning.orderservice.model.OrderEntity;
import com.learning.orderservice.model.OrderStatus;
import com.learning.orderservice.repository.OrderRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderProcessingService {

    private final OrderRepository orderRepository;

    public OrderProcessingService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Async("orderTaskExecutor")
    public CompletableFuture<OrderStatus> processOrder(Long orderId) {

        System.out.println(
                "Processing order " + orderId +
                        " on thread: " + Thread.currentThread().getName()
        );

        try {
            // Simulate inventory checking
            Thread.sleep(5000);

            OrderEntity order = orderRepository.findById(orderId)
                    .orElseThrow(() ->
                            new IllegalStateException("Order not found")
                    );

            boolean available =
                    ThreadLocalRandom.current().nextBoolean();

            OrderStatus status = available
                    ? OrderStatus.CONFIRMED
                    : OrderStatus.REJECTED;

            order.setStatus(status);

            orderRepository.save(order);

            System.out.println(
                    "Order " + orderId +
                            " processed with status: " + status
            );

            return CompletableFuture.completedFuture(status);

        } catch (InterruptedException ex) {

            Thread.currentThread().interrupt();

            throw new IllegalStateException(
                    "Order processing interrupted: " + orderId,
                    ex
            );
        }
    }
}