package com.learning.orderservice.client;

import com.learning.orderservice.dto.InventoryReservationRequest;
import com.learning.orderservice.dto.InventoryReservationResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class InventoryClient {

    private final RestClient restClient;

    public InventoryClient(
            RestClient.Builder restClientBuilder,
            @Value("${inventory.service.base-url}") String inventoryBaseUrl) {

        this.restClient = restClientBuilder
                .baseUrl(inventoryBaseUrl)
                .build();
    }

    public InventoryReservationResponse reserveStock(
            String productCode,
            Long orderId,
            Integer quantity) {

        InventoryReservationRequest request =
                new InventoryReservationRequest(orderId, quantity);

        return restClient.post()
                .uri("/api/inventory/products/{productCode}/reserve", productCode)
                .body(request)
                .retrieve()
                .body(InventoryReservationResponse.class);
    }
}