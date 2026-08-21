package com.example.transfer_service.dto;

public class StockCheckResponse {
    private Long productId;
    private Long warehouseId;
    private Integer availableQuantity;
    private boolean available;

    public StockCheckResponse() {}

    public Long getProductId() { return productId; }
    public Long getWarehouseId() { return warehouseId; }
    public Integer getAvailableQuantity() { return availableQuantity; }
    public boolean isAvailable() { return available; }
}
