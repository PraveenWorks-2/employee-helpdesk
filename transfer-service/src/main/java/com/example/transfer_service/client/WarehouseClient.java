package com.example.transfer_service.client;

import com.example.transfer_service.dto.InventoryTransferRequest;
import com.example.transfer_service.dto.StockCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "warehouse-service")
public interface WarehouseClient {
    @GetMapping("/api/inventory/check")
    StockCheckResponse checkStock(
            @RequestParam Long warehouseId,
            @RequestParam Long productId,
            @RequestParam Integer quantity);

    @PostMapping("/api/inventory/transfer")
    String transferStock(@RequestBody InventoryTransferRequest request);
}
