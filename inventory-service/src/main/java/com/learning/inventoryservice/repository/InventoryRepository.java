package com.learning.inventoryservice.repository;

import com.learning.inventoryservice.model.ProductInventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface InventoryRepository
        extends JpaRepository<ProductInventory, Long> {

    Optional<ProductInventory> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    SELECT p FROM ProductInventory p
    WHERE p.productCode = :productCode
""")
    Optional<ProductInventory> findByProductCodeForUpdate(
            @Param("productCode") String productCode
    );
}