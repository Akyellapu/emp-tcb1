package com.qentelli.employeetrackingsystem.exception;
public class DuplicatePersonException extends RuntimeException {
    public DuplicatePersonException(String message) {
        super(message);
    }
}