package com.learning.inventoryservice.dto;

import java.time.Instant;

public record InventoryResponse(
        Long id,
        String productCode,
        String productName,
        int availableQuantity,
        Instant createdAt
) {
}