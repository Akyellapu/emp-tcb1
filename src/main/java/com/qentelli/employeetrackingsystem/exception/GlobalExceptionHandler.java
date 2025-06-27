package com.qentelli.employeetrackingsystem.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AuthResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        AuthResponse<Object> response = new AuthResponse<>(
            HttpStatus.UNAUTHORIZED.value(),
            RequestProcessStatus.FAILURE,
            LocalDateTime.now(),
            "Login failed",
            null
        );
        response.setErrorCode(HttpStatus.UNAUTHORIZED);
        response.setErrorDescription("Invalid username or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AuthResponse<Object>> handleUsernameNotFound(UsernameNotFoundException ex) {
        AuthResponse<Object> response = new AuthResponse<>(
            HttpStatus.NOT_FOUND.value(),
            RequestProcessStatus.FAILURE,
            LocalDateTime.now(),
            "User lookup failed",
            null
        );
        response.setErrorCode(HttpStatus.NOT_FOUND);
        response.setErrorDescription("User not found with the provided username");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleManagerNotFoundException(ManagerNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI()); // Dynamically fetch the request path

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<AuthResponse<Object>> handleDuplicateAccount(DuplicateAccountException ex) {
        AuthResponse<Object> response = new AuthResponse<>(
            HttpStatus.CONFLICT.value(),
            RequestProcessStatus.FAILURE,
            LocalDateTime.now(),
            "Account creation failed",
            null
        );
        response.setErrorCode(HttpStatus.CONFLICT);
        response.setErrorDescription(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    
    
    @ExceptionHandler(DuplicateProjectException.class)
    public ResponseEntity<AuthResponse<Object>> handleDuplicateProject(DuplicateProjectException ex) {
        AuthResponse<Object> response = new AuthResponse<>(
            HttpStatus.CONFLICT.value(),
            RequestProcessStatus.FAILURE,
            LocalDateTime.now(),
            "project creation failed",
            null
        );
        response.setErrorCode(HttpStatus.CONFLICT);
        response.setErrorDescription(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}