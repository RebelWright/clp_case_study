package com.example.CLP_Case_Study.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {}
    public ResourceNotFoundException(String message) {
        super(message);

    }
}
