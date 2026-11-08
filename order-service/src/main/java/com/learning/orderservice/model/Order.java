
package com.learning.orderservice.model;

import java.time.Instant;

public record Order(
        Long id,
        String productCode,
        int quantity,
        String customerEmail,
        OrderStatus status,
        Instant createdAt
) {
}
