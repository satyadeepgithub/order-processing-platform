
package com.learning.orderservice.service;

import com.learning.orderservice.client.InventoryClient;
import com.learning.orderservice.dto.InventoryReservationResponse;
import com.learning.orderservice.model.OrderEntity;
import com.learning.orderservice.model.OrderStatus;
import com.learning.orderservice.repository.OrderRepository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderProcessingService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderProcessingService(
            OrderRepository orderRepository,
            InventoryClient inventoryClient) {

        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    @Async("orderTaskExecutor")
    public CompletableFuture<OrderStatus> processOrder(Long orderId) {

        System.out.println(
                "Processing order " + orderId +
                        " on thread: " + Thread.currentThread().getName()
        );

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalStateException("Order not found: " + orderId)
                );

        try {

            // Call Inventory Service to reserve stock
            InventoryReservationResponse response =
                    inventoryClient.reserveStock(
                            order.getProductCode(),
                            order.getId(),
                            order.getQuantity()
                    );

            OrderStatus status =
                    "RESERVED".equals(response.status())
                            ? OrderStatus.CONFIRMED
                            : OrderStatus.REJECTED;

            order.setStatus(status);
            orderRepository.save(order);

            System.out.println(
                    "Order " + orderId +
                            " processed with status: " + status
            );

            return CompletableFuture.completedFuture(status);

        } catch (Exception ex) {

            System.err.println(
                    "Inventory processing failed for order " + orderId +
                            ": " + ex.getMessage()
            );

            throw new IllegalStateException(
                    "Unable to process inventory reservation for order: "
                            + orderId,
                    ex
            );
        }
    }
}
