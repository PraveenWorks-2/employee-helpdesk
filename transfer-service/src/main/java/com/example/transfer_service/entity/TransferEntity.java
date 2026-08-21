package com.example.transfer_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transfer_entity")
public class TransferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_warehouse_id")
    private Long sourceWarehouseId;

    @Column(name = "destination_warehouse_id")
    private Long destinationWarehouseId;

    @Column(name = "product_id")
    private Long productId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    public TransferEntity() {}

    public Long getId() { return id; }
    public Long getSourceWarehouseId() { return sourceWarehouseId; }
    public void setSourceWarehouseId(Long sourceWarehouseId) { this.sourceWarehouseId = sourceWarehouseId; }
    public Long getDestinationWarehouseId() { return destinationWarehouseId; }
    public void setDestinationWarehouseId(Long destinationWarehouseId) { this.destinationWarehouseId = destinationWarehouseId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public TransferStatus getStatus() { return status; }
    public void setStatus(TransferStatus status) { this.status = status; }
}
