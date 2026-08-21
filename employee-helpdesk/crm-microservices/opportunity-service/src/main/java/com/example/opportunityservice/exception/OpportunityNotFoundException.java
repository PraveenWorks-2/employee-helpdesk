package com.example.opportunityservice.exception;

public class OpportunityNotFoundException extends RuntimeException {

    public OpportunityNotFoundException(String message) {
        super(message);
    }
}