package com.example.warehouse_service.repository;

import com.example.warehouse_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByWarehouseId(Long warehouseId);
    Optional<Inventory> findByIdAndWarehouseId(Long id, Long warehouseId);
}
