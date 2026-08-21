package com.example.warehouse_service.service;

import com.example.warehouse_service.dto.InventoryTransferRequest;
import com.example.warehouse_service.dto.StockCheckResponse;
import com.example.warehouse_service.entity.Inventory;
import com.example.warehouse_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public StockCheckResponse checkStock(Long warehouseId, Long productCode, Integer quantity) {
        Inventory inventory = inventoryRepository.findByIdAndWarehouseId(productCode, warehouseId)
                .orElseThrow(() -> new RuntimeException("Product not found in warehouse"));

        return new StockCheckResponse(
                productCode, warehouseId, inventory.getQuantity(),
                inventory.getQuantity() >= quantity
        );
    }

    @Transactional
    public void transferStock(InventoryTransferRequest request) {
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        if (request.getSourceWarehouseId().equals(request.getDestinationWarehouseId())) {
            throw new RuntimeException("Source and destination warehouses must be different");
        }

        Inventory source = inventoryRepository.findByIdAndWarehouseId(
                request.getProductId(), request.getSourceWarehouseId())
                .orElseThrow(() -> new RuntimeException("Product not found in source warehouse"));

        if (source.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        source.setQuantity(source.getQuantity() - request.getQuantity());
        inventoryRepository.save(source);

        Inventory destination = inventoryRepository.findByIdAndWarehouseId(
                request.getProductId(), request.getDestinationWarehouseId()).orElse(null);

        if (destination != null) {
            destination.setQuantity(destination.getQuantity() + request.getQuantity());
            inventoryRepository.save(destination);
        } else {
            Inventory newInventory = new Inventory();
            newInventory.setProductCode(source.getProductCode());
            newInventory.setProductName(source.getProductName());
            newInventory.setQuantity(request.getQuantity());
            newInventory.setWarehouseId(request.getDestinationWarehouseId());
            inventoryRepository.save(newInventory);
        }
    }
}
