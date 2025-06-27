package com.qentelli.employeetrackingsystem.exception;
public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException(String message) {
        super(message);
    }
}