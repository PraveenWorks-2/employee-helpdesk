package com.example.warehouse_service.dto;

public class StockCheckResponse {
    private Long productId;
    private Long warehouseId;
    private Integer availableQuantity;
    private boolean available;

    public StockCheckResponse() {}

    public StockCheckResponse(Long productId, Long warehouseId,
                              Integer availableQuantity, boolean available) {
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.availableQuantity = availableQuantity;
        this.available = available;
    }

    public Long getProductId() { return productId; }
    public Long getWarehouseId() { return warehouseId; }
    public Integer getAvailableQuantity() { return availableQuantity; }
    public boolean isAvailable() { return available; }
}
