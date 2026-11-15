package com.learning.inventoryservice.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "product_inventory",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_code",
                        columnNames = "product_code"
                )
        }
)
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int availableQuantity;

    @Column(nullable = false)
    private Instant createdAt;

    protected ProductInventory() {
    }

    public ProductInventory(
            String productCode,
            String productName,
            int availableQuantity) {

        this.productCode = productCode;
        this.productName = productName;
        this.availableQuantity = availableQuantity;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}