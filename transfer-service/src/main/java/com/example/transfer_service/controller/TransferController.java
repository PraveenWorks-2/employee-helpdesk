package com.example.transfer_service.controller;

import com.example.transfer_service.dto.TransferRequest;
import com.example.transfer_service.entity.TransferEntity;
import com.example.transfer_service.service.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public TransferEntity createTransfer(@RequestBody TransferRequest request) {
        return transferService.createTransfer(request);
    }

    @GetMapping("/{id}")
    public TransferEntity getTransfer(@PathVariable Long id) {
        return transferService.getTransfer(id);
    }

    @PostMapping("/{id}/approve")
    public TransferEntity approveTransfer(@PathVariable Long id) {
        return transferService.approveTransfer(id);
    }

    @PostMapping("/{id}/complete")
    public TransferEntity completeTransfer(@PathVariable Long id) {
        return transferService.completeTransfer(id);
    }
}
