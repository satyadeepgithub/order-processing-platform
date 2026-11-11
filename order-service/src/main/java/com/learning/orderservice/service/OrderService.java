package com.learning.orderservice.service;

import com.learning.orderservice.dto.CreateOrderRequest;
import com.learning.orderservice.dto.OrderResponse;
import com.learning.orderservice.model.OrderEntity;
import com.learning.orderservice.model.OrderStatus;
import com.learning.orderservice.repository.OrderRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProcessingService orderProcessingService;

    public OrderService(
            OrderRepository orderRepository,
            OrderProcessingService orderProcessingService) {

        this.orderRepository = orderRepository;
        this.orderProcessingService = orderProcessingService;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {

        OrderEntity order = new OrderEntity(
                request.productCode(),
                request.quantity(),
                request.customerEmail(),
                OrderStatus.PENDING,
                Instant.now()
        );

        OrderEntity savedOrder = orderRepository.save(order);

        orderProcessingService.processOrder(savedOrder.getId())
                .whenComplete((status, exception) -> {

                    if (exception != null) {
                        System.err.println(
                                "Order processing failed for order "
                                        + savedOrder.getId()
                        );
                        exception.printStackTrace();
                    } else {
                        System.out.println(
                                "Background processing completed: "
                                        + status
                        );
                    }
                });

        return toResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long orderId) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found: " + orderId
                ));

        return toResponse(order);
    }

    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(OrderEntity::getId))
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(OrderEntity order) {

        return new OrderResponse(
                order.getId(),
                order.getProductCode(),
                order.getQuantity(),
                order.getCustomerEmail(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}