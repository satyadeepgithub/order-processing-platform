package com.learning.inventoryservice.repository;

import com.learning.inventoryservice.model.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository
        extends JpaRepository<InventoryReservation, Long> {

    Optional<InventoryReservation> findByOrderId(Long orderId);
}