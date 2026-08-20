package com.enterprise.budgetapproval.budget.exception;

public class InvalidBudgetOperationException extends RuntimeException {

    public InvalidBudgetOperationException(String message) {
        super(message);
    }
}