package com.example.warehouse_service.controller;

import com.example.warehouse_service.dto.InventoryTransferRequest;
import com.example.warehouse_service.dto.StockCheckResponse;
import com.example.warehouse_service.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/check")
    public StockCheckResponse checkStock(
            @RequestParam Long warehouseId,
            @RequestParam Long productCode,
            @RequestParam Integer quantity) {
        return inventoryService.checkStock(warehouseId, productCode, quantity);
    }

    @PostMapping("/transfer")
    public String transferStock(@RequestBody InventoryTransferRequest request) {
        inventoryService.transferStock(request);
        return "Inventory updated successfully";
    }
    
}
