package com.learning.orderservice.dto;

import java.time.Instant;

public record InventoryReservationResponse(
        Long orderId,
        String productCode,
        int quantity,
        String status,
        Instant createdAt
) {
}