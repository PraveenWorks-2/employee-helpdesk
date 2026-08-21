package com.example.warehouse_service.controller;

import com.example.warehouse_service.entity.Inventory;
import com.example.warehouse_service.entity.Warehouse;
import com.example.warehouse_service.repository.InventoryRepository;
import com.example.warehouse_service.repository.WarehouseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;

    public WarehouseController(WarehouseRepository warehouseRepository,
                               InventoryRepository inventoryRepository) {
        this.warehouseRepository = warehouseRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouse(@PathVariable Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
    }

    @GetMapping("/{id}/inventory")
    public List<Inventory> getInventory(@PathVariable Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new RuntimeException("Warehouse not found");
        }
        return inventoryRepository.findByWarehouseId(id);
    }
    
    @GetMapping("/inventory")
    public List<Inventory> getInventoryAll()
    {
    	return inventoryRepository.findAll();
    }
}
