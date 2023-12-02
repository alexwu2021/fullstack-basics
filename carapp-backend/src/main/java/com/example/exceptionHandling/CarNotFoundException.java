package com.example.exceptionHandling;

public class CarNotFoundException extends RuntimeException {

    CarNotFoundException(Long id) {
        super("Could not find car " + id);
    }
}