package com.learning.inventoryservice.service;

import com.learning.inventoryservice.dto.CreateInventoryRequest;
import com.learning.inventoryservice.dto.InventoryResponse;
import com.learning.inventoryservice.model.ProductInventory;
import com.learning.inventoryservice.repository.InventoryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.learning.inventoryservice.dto.ReserveInventoryRequest;
import com.learning.inventoryservice.dto.ReservationResponse;
import com.learning.inventoryservice.model.InventoryReservation;
import com.learning.inventoryservice.model.ReservationStatus;
import com.learning.inventoryservice.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;

    public InventoryService(
            InventoryRepository inventoryRepository,
            ReservationRepository reservationRepository) {

        this.inventoryRepository = inventoryRepository;
        this.reservationRepository = reservationRepository;
    }

    public InventoryResponse addProduct(CreateInventoryRequest request) {

        if (inventoryRepository.existsByProductCode(
                request.productCode())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Product already exists: " + request.productCode()
            );
        }

        ProductInventory product = new ProductInventory(
                request.productCode(),
                request.productName(),
                request.availableQuantity()
        );

        ProductInventory savedProduct =
                inventoryRepository.save(product);

        return toResponse(savedProduct);
    }

    public InventoryResponse getProduct(String productCode) {

        ProductInventory product =
                inventoryRepository.findByProductCode(productCode)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Product not found: " + productCode
                                )
                        );

        return toResponse(product);
    }

    public List<InventoryResponse> getAllProducts() {

        return inventoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ReservationResponse reserveStock(
            String productCode,
            ReserveInventoryRequest request) {

        // Lock the product row while this transaction is running.
        ProductInventory product = inventoryRepository
                .findByProductCodeForUpdate(productCode)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found: " + productCode
                ));

        // Check whether this order has already requested a reservation.
        var existingReservation = reservationRepository
                .findByOrderId(request.orderId());

        if (existingReservation.isPresent()) {

            InventoryReservation existing = existingReservation.get();

            // Same order ID must not be reused for different details.
            if (!existing.getProductCode().equals(productCode)
                    || existing.getQuantity() != request.quantity()) {

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Order ID already used for a different reservation"
                );
            }

            // Return the original result without deducting stock again.
            return toReservationResponse(existing);
        }

        ReservationStatus status;

        if (product.getAvailableQuantity() >= request.quantity()) {

            product.setAvailableQuantity(
                    product.getAvailableQuantity() - request.quantity()
            );

            inventoryRepository.save(product);

            status = ReservationStatus.RESERVED;

        } else {

            status = ReservationStatus.REJECTED;
        }

        InventoryReservation reservation = new InventoryReservation(
                request.orderId(),
                productCode,
                request.quantity(),
                status
        );

        InventoryReservation savedReservation =
                reservationRepository.save(reservation);

        return toReservationResponse(savedReservation);
    }

    private ReservationResponse toReservationResponse(
            InventoryReservation reservation) {

        return new ReservationResponse(
                reservation.getOrderId(),
                reservation.getProductCode(),
                reservation.getQuantity(),
                reservation.getStatus().name(),
                reservation.getCreatedAt()
        );
    }

    private InventoryResponse toResponse(ProductInventory product) {

        return new InventoryResponse(
                product.getId(),
                product.getProductCode(),
                product.getProductName(),
                product.getAvailableQuantity(),
                product.getCreatedAt()
        );
    }
}