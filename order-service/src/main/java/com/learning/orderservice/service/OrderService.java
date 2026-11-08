
package com.learning.orderservice.service;

import com.learning.orderservice.dto.CreateOrderRequest;
import com.learning.orderservice.dto.OrderResponse;
import com.learning.orderservice.model.Order;
import com.learning.orderservice.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final AtomicLong idGenerator = new AtomicLong(0);

    private final ConcurrentMap<Long, Order> orders =
            new ConcurrentHashMap<>();

    public OrderResponse createOrder(CreateOrderRequest request) {

        long orderId = idGenerator.incrementAndGet();

        Order order = new Order(
                orderId,
                request.productCode(),
                request.quantity(),
                request.customerEmail(),
                OrderStatus.PENDING,
                Instant.now()
        );

        orders.put(orderId, order);

        return toResponse(order);
    }

    public OrderResponse getOrderById(Long orderId) {

        Order order = orders.get(orderId);

        if (order == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Order not found: " + orderId
            );
        }

        return toResponse(order);
    }

    public List<OrderResponse> getAllOrders() {

        return orders.values()
                .stream()
                .sorted(Comparator.comparing(Order::id))
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(Order order) {

        return new OrderResponse(
                order.id(),
                order.productCode(),
                order.quantity(),
                order.customerEmail(),
                order.status(),
                order.createdAt()
        );
    }
}
