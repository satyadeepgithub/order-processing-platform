package com.learning.inventoryservice.dto;

import java.time.Instant;

public record ReservationResponse(
        Long orderId,
        String productCode,
        int quantity,
        String status,
        Instant createdAt
) {}