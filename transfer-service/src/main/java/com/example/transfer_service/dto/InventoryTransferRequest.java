package com.example.transfer_service.dto;

public class InventoryTransferRequest {
    private Long sourceWarehouseId;
    private Long destinationWarehouseId;
    private Long productId;
    private Integer quantity;

    public InventoryTransferRequest() {}

    public InventoryTransferRequest(Long sourceWarehouseId, Long destinationWarehouseId,
                                    Long productId, Integer quantity) {
        this.sourceWarehouseId = sourceWarehouseId;
        this.destinationWarehouseId = destinationWarehouseId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getSourceWarehouseId() { return sourceWarehouseId; }
    public Long getDestinationWarehouseId() { return destinationWarehouseId; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
}
