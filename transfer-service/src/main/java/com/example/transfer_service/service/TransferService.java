package com.example.transfer_service.service;

import com.example.transfer_service.client.WarehouseClient;
import com.example.transfer_service.dto.InventoryTransferRequest;
import com.example.transfer_service.dto.StockCheckResponse;
import com.example.transfer_service.dto.TransferRequest;
import com.example.transfer_service.entity.TransferEntity;
import com.example.transfer_service.entity.TransferStatus;
import com.example.transfer_service.repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final WarehouseClient warehouseClient;

    public TransferService(TransferRepository transferRepository,
                           WarehouseClient warehouseClient) {
        this.transferRepository = transferRepository;
        this.warehouseClient = warehouseClient;
    }

    public TransferEntity createTransfer(TransferRequest request) {
        validate(request);

        StockCheckResponse stock = warehouseClient.checkStock(
                request.getSourceWarehouseId(),
                request.getProductId(),
                request.getQuantity());

        if (!stock.isAvailable()) {
            throw new RuntimeException(
                    "Insufficient stock. Available: " + stock.getAvailableQuantity());
        }

        TransferEntity transfer = new TransferEntity();
        transfer.setSourceWarehouseId(request.getSourceWarehouseId());
        transfer.setDestinationWarehouseId(request.getDestinationWarehouseId());
        transfer.setProductId(request.getProductId());
        transfer.setQuantity(request.getQuantity());
        transfer.setStatus(TransferStatus.PENDING);

        return transferRepository.save(transfer);
    }

    public TransferEntity getTransfer(Long id) {
        return transferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));
    }

    public TransferEntity approveTransfer(Long id) {
        TransferEntity transfer = getTransfer(id);

        if (transfer.getStatus() != TransferStatus.PENDING) {
            throw new RuntimeException("Only PENDING transfer can be approved");
        }

        transfer.setStatus(TransferStatus.APPROVED);
        return transferRepository.save(transfer);
    }

    public TransferEntity completeTransfer(Long id) {
        TransferEntity transfer = getTransfer(id);

        if (transfer.getStatus() != TransferStatus.APPROVED) {
            throw new RuntimeException("Only APPROVED transfer can be completed");
        }

        StockCheckResponse stock = warehouseClient.checkStock(
                transfer.getSourceWarehouseId(),
                transfer.getProductId(),
                transfer.getQuantity());

        if (!stock.isAvailable()) {
            transfer.setStatus(TransferStatus.REJECTED);
            transferRepository.save(transfer);
            throw new RuntimeException("Insufficient stock during completion");
        }

        InventoryTransferRequest request = new InventoryTransferRequest(
                transfer.getSourceWarehouseId(),
                transfer.getDestinationWarehouseId(),
                transfer.getProductId(),
                transfer.getQuantity());

        warehouseClient.transferStock(request);

        transfer.setStatus(TransferStatus.COMPLETED);
        return transferRepository.save(transfer);
    }

    private void validate(TransferRequest request) {
        if (request.getSourceWarehouseId() == null ||
            request.getDestinationWarehouseId() == null ||
            request.getProductId() == null ||
            request.getQuantity() == null) {
            throw new RuntimeException("All transfer fields are required");
        }
        if (request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }
        if (request.getSourceWarehouseId().equals(request.getDestinationWarehouseId())) {
            throw new RuntimeException("Source and destination warehouses must be different");
        }
    }
}
