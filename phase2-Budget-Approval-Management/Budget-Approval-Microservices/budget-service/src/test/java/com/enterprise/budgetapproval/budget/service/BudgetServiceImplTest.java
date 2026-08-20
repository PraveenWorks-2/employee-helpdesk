package com.enterprise.budgetapproval.budget.service;

import com.enterprise.budgetapproval.budget.dto.request.BudgetRequest;
import com.enterprise.budgetapproval.budget.dto.response.BudgetResponse;
import com.enterprise.budgetapproval.budget.dto.response.VarianceResponse;
import com.enterprise.budgetapproval.budget.entity.Budget;
import com.enterprise.budgetapproval.budget.exception.InvalidBudgetOperationException;
import com.enterprise.budgetapproval.budget.exception.ResourceNotFoundException;
import com.enterprise.budgetapproval.budget.repository.AllocationRepository;
import com.enterprise.budgetapproval.budget.repository.BudgetRepository;
import com.enterprise.budgetapproval.budget.service.impl.BudgetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private AllocationRepository allocationRepository;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    private Budget sampleBudget;

    @BeforeEach
    void setUp() {
        sampleBudget = new Budget();
        sampleBudget.setId(1L);
        sampleBudget.setDepartmentName("Engineering");
        sampleBudget.setFiscalYear(2026);
        sampleBudget.setTotalAmount(BigDecimal.valueOf(500000));
        sampleBudget.setSpentAmount(BigDecimal.ZERO);
        sampleBudget.setStatus(Budget.BudgetStatus.PENDING);
    }

    @Test
    void createBudget_savesAndReturnsBudget() {
        BudgetRequest request = new BudgetRequest();
        request.setDepartmentName("Engineering");
        request.setFiscalYear(2026);
        request.setTotalAmount(BigDecimal.valueOf(500000));

        when(budgetRepository.save(any(Budget.class))).thenReturn(sampleBudget);

        BudgetResponse response = budgetService.createBudget(request);

        assertEquals("Engineering", response.getDepartmentName());
        assertEquals(Budget.BudgetStatus.PENDING, response.getStatus());
        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    @Test
    void getBudgetById_whenFound_returnsBudget() {
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(sampleBudget));

        BudgetResponse response = budgetService.getBudgetById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Engineering", response.getDepartmentName());
    }

    @Test
    void getBudgetById_whenNotFound_throwsException() {
        when(budgetRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> budgetService.getBudgetById(99L));
    }

    @Test
    void getVariance_calculatesCorrectly() {
        sampleBudget.setSpentAmount(BigDecimal.valueOf(100000));
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(sampleBudget));

        VarianceResponse response = budgetService.getVariance(1L);

        assertEquals(BigDecimal.valueOf(400000), response.getVariance());
    }

    @Test
    void updateStatus_whenPending_updatesSuccessfully() {
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(sampleBudget));
        when(budgetRepository.save(any(Budget.class))).thenReturn(sampleBudget);

        BudgetResponse response = budgetService.updateStatus(1L, Budget.BudgetStatus.APPROVED);

        assertNotNull(response);
        verify(budgetRepository, times(1)).save(any(Budget.class));
    }

    @Test
    void updateStatus_whenAlreadyApproved_throwsException() {
        sampleBudget.setStatus(Budget.BudgetStatus.APPROVED);
        when(budgetRepository.findById(1L)).thenReturn(Optional.of(sampleBudget));

        assertThrows(InvalidBudgetOperationException.class,
                () -> budgetService.updateStatus(1L, Budget.BudgetStatus.REJECTED));
    }
}