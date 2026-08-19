package com.example.purchase_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.purchase_service.dto.PurchaseItemRequestDto;
import com.example.purchase_service.dto.PurchaseRequestDto;
import com.example.purchase_service.dto.PurchaseResponseDto;
import com.example.purchase_service.entity.PurchaseItem;
import com.example.purchase_service.entity.PurchaseRequest;
import com.example.purchase_service.exception.ResourceNotFoundException;
import com.example.purchase_service.repository.PurchaseRequestRepository;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private PurchaseRequestRepository purchaseRequestRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    private PurchaseRequestDto purchaseRequestDto;

    @BeforeEach
    void setUp() {

        PurchaseItemRequestDto item1 =
                new PurchaseItemRequestDto();

        item1.setItemName("Laptop");
        item1.setDescription("Dell Laptop");
        item1.setQuantity(2);
        item1.setUnitPrice(new BigDecimal("50000.00"));

        PurchaseItemRequestDto item2 =
                new PurchaseItemRequestDto();

        item2.setItemName("Mouse");
        item2.setDescription("Wireless Mouse");
        item2.setQuantity(2);
        item2.setUnitPrice(new BigDecimal("1000.00"));

        purchaseRequestDto =
                new PurchaseRequestDto();

        purchaseRequestDto.setEmployeeId(101L);
        purchaseRequestDto.setTitle("Office Equipment");
        purchaseRequestDto.setDescription(
                "Purchase equipment for employee"
        );

        purchaseRequestDto.setItems(
                List.of(item1, item2)
        );
    }

    // ---------------------------------------------------------
    // 1. CREATE PURCHASE - SUCCESS
    // ---------------------------------------------------------

    @Test
    void createPurchase_success() {

        PurchaseRequest savedPurchase =
                new PurchaseRequest();

        savedPurchase.setId(1L);
        savedPurchase.setRequestNumber("PR-123456");
        savedPurchase.setEmployeeId(101L);
        savedPurchase.setTitle("Office Equipment");
        savedPurchase.setDescription(
                "Purchase equipment for employee"
        );
        savedPurchase.setStatus("PENDING");

        savedPurchase.setTotalAmount(
                new BigDecimal("102000.00")
        );

        savedPurchase.setCreatedAt(
                LocalDateTime.now()
        );

        savedPurchase.setUpdatedAt(
                LocalDateTime.now()
        );

        PurchaseItem item1 =
                new PurchaseItem();

        item1.setId(1L);
        item1.setItemName("Laptop");
        item1.setDescription("Dell Laptop");
        item1.setQuantity(2);
        item1.setUnitPrice(
                new BigDecimal("50000.00")
        );

        PurchaseItem item2 =
                new PurchaseItem();

        item2.setId(2L);
        item2.setItemName("Mouse");
        item2.setDescription("Wireless Mouse");
        item2.setQuantity(2);
        item2.setUnitPrice(
                new BigDecimal("1000.00")
        );

        item1.setPurchaseRequest(savedPurchase);
        item2.setPurchaseRequest(savedPurchase);

        savedPurchase.setItems(
                List.of(item1, item2)
        );

        when(purchaseRequestRepository.save(any(PurchaseRequest.class)))
                .thenReturn(savedPurchase);

        PurchaseResponseDto response =
                purchaseService.createPurchase(
                        purchaseRequestDto
                );

        assertNotNull(response);

        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                "PR-123456",
                response.getRequestNumber()
        );

        assertEquals(
                101L,
                response.getEmployeeId()
        );

        assertEquals(
                "Office Equipment",
                response.getTitle()
        );

        assertEquals(
                "PENDING",
                response.getStatus()
        );

        assertEquals(
                new BigDecimal("102000.00"),
                response.getTotalAmount()
        );

        assertNotNull(response.getItems());

        assertEquals(
                2,
                response.getItems().size()
        );

        verify(
                purchaseRequestRepository,
                times(1)
        ).save(any(PurchaseRequest.class));
    }

    // ---------------------------------------------------------
    // 2. GET ALL PURCHASES
    // ---------------------------------------------------------

    @Test
    void getAllPurchases_success() {

        PurchaseRequest purchase1 =
                createPurchaseEntity(
                        1L,
                        "PR-1001",
                        "Laptop Purchase",
                        "PENDING",
                        new BigDecimal("50000.00")
                );

        PurchaseRequest purchase2 =
                createPurchaseEntity(
                        2L,
                        "PR-1002",
                        "Office Chair",
                        "APPROVED",
                        new BigDecimal("10000.00")
                );

        when(purchaseRequestRepository.findAll())
                .thenReturn(
                        List.of(purchase1, purchase2)
                );

        List<PurchaseResponseDto> response =
                purchaseService.getAllPurchases();

        assertNotNull(response);

        assertEquals(
                2,
                response.size()
        );

        assertEquals(
                "PR-1001",
                response.get(0).getRequestNumber()
        );

        assertEquals(
                "PR-1002",
                response.get(1).getRequestNumber()
        );

        assertEquals(
                "PENDING",
                response.get(0).getStatus()
        );

        assertEquals(
                "APPROVED",
                response.get(1).getStatus()
        );

        verify(
                purchaseRequestRepository,
                times(1)
        ).findAll();
    }

    // ---------------------------------------------------------
    // 3. GET PURCHASE BY ID - SUCCESS
    // ---------------------------------------------------------

    @Test
    void getPurchaseById_success() {

        PurchaseRequest purchase =
                createPurchaseEntity(
                        1L,
                        "PR-1001",
                        "Laptop Purchase",
                        "PENDING",
                        new BigDecimal("50000.00")
                );

        when(purchaseRequestRepository.findById(1L))
                .thenReturn(
                        Optional.of(purchase)
                );

        PurchaseResponseDto response =
                purchaseService.getPurchaseById(1L);

        assertNotNull(response);

        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                "PR-1001",
                response.getRequestNumber()
        );

        assertEquals(
                "Laptop Purchase",
                response.getTitle()
        );

        assertEquals(
                "PENDING",
                response.getStatus()
        );

        assertEquals(
                new BigDecimal("50000.00"),
                response.getTotalAmount()
        );

        verify(
                purchaseRequestRepository,
                times(1)
        ).findById(1L);
    }

    // ---------------------------------------------------------
    // 4. GET PURCHASE BY ID - NOT FOUND
    // ---------------------------------------------------------

    @Test
    void getPurchaseById_notFound() {

        when(purchaseRequestRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> purchaseService.getPurchaseById(999L)
                );

        assertEquals(
                "Purchase request not found: 999",
                exception.getMessage()
        );

        verify(
                purchaseRequestRepository,
                times(1)
        ).findById(999L);
    }

    // ---------------------------------------------------------
    // 5. UPDATE PURCHASE STATUS - SUCCESS
    // ---------------------------------------------------------

    @Test
    void updatePurchaseStatus_success() {

        PurchaseRequest purchase =
                createPurchaseEntity(
                        1L,
                        "PR-1001",
                        "Laptop Purchase",
                        "PENDING",
                        new BigDecimal("50000.00")
                );

        when(purchaseRequestRepository.findById(1L))
                .thenReturn(
                        Optional.of(purchase)
                );

        when(purchaseRequestRepository.save(any(PurchaseRequest.class)))
                .thenAnswer(
                        invocation -> invocation.getArgument(0)
                );

        PurchaseResponseDto response =
                purchaseService.updatePurchaseStatus(
                        1L,
                        "APPROVED"
                );

        assertNotNull(response);

        assertEquals(
                "APPROVED",
                response.getStatus()
        );

        assertEquals(
                1L,
                response.getId()
        );

        verify(
                purchaseRequestRepository,
                times(1)
        ).findById(1L);

        verify(
                purchaseRequestRepository,
                times(1)
        ).save(any(PurchaseRequest.class));
    }

    // ---------------------------------------------------------
    // 6. UPDATE PURCHASE STATUS - NOT FOUND
    // ---------------------------------------------------------

    @Test
    void updatePurchaseStatus_notFound() {

        when(purchaseRequestRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> purchaseService.updatePurchaseStatus(
                                999L,
                                "APPROVED"
                        )
                );

        assertEquals(
                "Purchase request not found: 999",
                exception.getMessage()
        );

        verify(
                purchaseRequestRepository,
                times(1)
        ).findById(999L);

        verify(
                purchaseRequestRepository,
                never()
        ).save(any(PurchaseRequest.class));
    }

  
    private PurchaseRequest createPurchaseEntity(
            Long id,
            String requestNumber,
            String title,
            String status,
            BigDecimal totalAmount) {

        PurchaseRequest purchase = new PurchaseRequest();
        purchase.setId(id);
        purchase.setRequestNumber( requestNumber);
        purchase.setEmployeeId(101L);
        purchase.setTitle(title);
        purchase.setDescription( "Test purchase");
        purchase.setTotalAmount(  totalAmount);
        purchase.setStatus(status);
        purchase.setCreatedAt( LocalDateTime.now() );
        purchase.setUpdatedAt( LocalDateTime.now());
        purchase.setItems( List.of() );

        return purchase;
    }
}