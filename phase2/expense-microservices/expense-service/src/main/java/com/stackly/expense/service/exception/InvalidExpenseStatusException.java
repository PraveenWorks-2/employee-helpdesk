package com.stackly.expense.service.exception;

public class InvalidExpenseStatusException extends RuntimeException {
    public InvalidExpenseStatusException(String message)
    {
        super(message);
    }
}
