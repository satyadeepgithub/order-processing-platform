package com.learning.orderservice.dto;

public record InventoryReservationRequest(
        Long orderId,
        Integer quantity
) {
}