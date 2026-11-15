package com.learning.inventoryservice.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "inventory_reservations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reservation_order_id",
                        columnNames = "order_id"
                )
        }
)
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    protected InventoryReservation() {
    }

    public InventoryReservation(
            Long orderId,
            String productCode,
            int quantity,
            ReservationStatus status) {

        this.orderId = orderId;
        this.productCode = productCode;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}