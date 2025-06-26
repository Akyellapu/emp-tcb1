package com.qentelli.employeetrackingsystem.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;

@RestControllerAdvice
public class EmployeeTrackingSystemDataExceptionHandler {

	@ExceptionHandler(InvalidInputDataException.class)
	@ResponseBody
	public ResponseEntity<?> handleInvalidInputDataException(InvalidInputDataException ex) {

		AuthResponse<String> authResponse = new AuthResponse<String>(ex.getStatus().value(), RequestProcessStatus.ERROR,
				LocalDateTime.now(), ex.getMessage(), null);
		return new ResponseEntity<>(authResponse, ex.getStatus());
	}

}
