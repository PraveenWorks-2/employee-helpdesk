package com.stackly.expense.service.controller;

import com.stackly.expense.service.dto.ExpenseRequestDto;
import com.stackly.expense.service.dto.ExpenseResponseDto;
import com.stackly.expense.service.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> createExpense
            (@Valid @RequestBody ExpenseRequestDto expenseRequestDto){
        ExpenseResponseDto savedExpenseResponseDto = expenseService.createExpense(expenseRequestDto);
        return  new ResponseEntity<>(savedExpenseResponseDto, HttpStatus.CREATED);
    }
   @GetMapping ("{id}")
    public ResponseEntity<ExpenseResponseDto> getById
           (@PathVariable ("id") Long id){
        ExpenseResponseDto expenseResponseDto = expenseService.getById(id);
        return ResponseEntity.ok(expenseResponseDto);
   }
   @PostMapping("/{id}/submit")
    public ResponseEntity<ExpenseResponseDto> submitExpense
           (@PathVariable("id") Long id){
        ExpenseResponseDto updatedExpenseResponseDto = expenseService.submitExpense(id);
        return ResponseEntity.ok(updatedExpenseResponseDto);
   }
}
