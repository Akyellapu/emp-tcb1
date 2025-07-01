package com.qentelli.employeetrackingsystem.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AuthResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());

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
        logger.warn("User not found: {}", ex.getMessage());

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

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<AuthResponse<Object>> handleDuplicateAccount(DuplicateAccountException ex) {
        logger.info("Duplicate account creation attempt: {}", ex.getMessage());

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
    
    @ExceptionHandler(DuplicatePersonException.class)
    public ResponseEntity<AuthResponse<Object>> handleDuplicatePerson(DuplicatePersonException ex) {
        logger.info("Duplicate person creation attempt: {}", ex.getMessage());

        AuthResponse<Object> response = new AuthResponse<>(
            HttpStatus.CONFLICT.value(),
            RequestProcessStatus.FAILURE,
            LocalDateTime.now(),
            "person creation failed",
            null
        );
        response.setErrorCode(HttpStatus.CONFLICT);
        response.setErrorDescription(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateProjectException.class)
    public ResponseEntity<AuthResponse<Object>> handleDuplicateProject(DuplicateProjectException ex) {
        logger.info("Duplicate project creation attempt: {}", ex.getMessage());

        AuthResponse<Object> response = new AuthResponse<>(
            HttpStatus.CONFLICT.value(),
            RequestProcessStatus.FAILURE,
            LocalDateTime.now(),
            "Project creation failed",
            null
        );
        response.setErrorCode(HttpStatus.CONFLICT);
        response.setErrorDescription(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<AuthResponse<Object>> handleManagerNotFoundException(PersonNotFoundException ex, HttpServletRequest request) {
        logger.warn("person not found: {}", ex.getMessage());

        AuthResponse<Object> response = new AuthResponse<>(
            HttpStatus.NOT_FOUND.value(),
            RequestProcessStatus.FAILURE,
            LocalDateTime.now(),
            "person not found for request path: " + request.getRequestURI(),
            null
        );
        response.setErrorCode(HttpStatus.NOT_FOUND);
        response.setErrorDescription(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuthResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        logger.debug("Validation failed: {}", errorMessages);

        AuthResponse<Object> response = new AuthResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                RequestProcessStatus.FAILURE,
                LocalDateTime.now(),
                errorMessages,
                null
        );
        response.setErrorCode(HttpStatus.BAD_REQUEST);
        response.setErrorDescription("Validation failure");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponse<Object>> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred", ex);

        AuthResponse<Object> response = new AuthResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                RequestProcessStatus.FAILURE,
                LocalDateTime.now(),
                "An unexpected error occurred",
                null
        );
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setErrorDescription(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}