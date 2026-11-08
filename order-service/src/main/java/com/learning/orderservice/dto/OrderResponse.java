
package com.learning.orderservice.dto;

import com.learning.orderservice.model.OrderStatus;

import java.time.Instant;

public record OrderResponse(
        Long orderId,
        String productCode,
        int quantity,
        String customerEmail,
        OrderStatus status,
        Instant createdAt
) {
}
