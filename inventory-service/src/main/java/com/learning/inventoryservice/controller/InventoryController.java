package com.learning.inventoryservice.controller;

import com.learning.inventoryservice.dto.CreateInventoryRequest;
import com.learning.inventoryservice.dto.InventoryResponse;
import com.learning.inventoryservice.service.InventoryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.learning.inventoryservice.dto.ReserveInventoryRequest;
import com.learning.inventoryservice.dto.ReservationResponse;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse addProduct(
            @Valid @RequestBody CreateInventoryRequest request) {

        return inventoryService.addProduct(request);
    }

    @GetMapping("/products/{productCode}")
    public InventoryResponse getProduct(
            @PathVariable String productCode) {

        return inventoryService.getProduct(productCode);
    }

    @GetMapping("/products")
    public List<InventoryResponse> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @PostMapping("/products/{productCode}/reserve")
    public ReservationResponse reserveStock(
            @PathVariable String productCode,
            @Valid @RequestBody ReserveInventoryRequest request) {

        return inventoryService.reserveStock(productCode, request);
    }
}