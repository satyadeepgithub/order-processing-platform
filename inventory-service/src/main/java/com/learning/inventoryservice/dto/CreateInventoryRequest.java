package com.learning.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateInventoryRequest(

        @NotBlank(message = "Product code is required")
        String productCode,

        @NotBlank(message = "Product name is required")
        String productName,

        @NotNull(message = "Available quantity is required")
        @Min(value = 0, message = "Available quantity cannot be negative")
        Integer availableQuantity

) {
}