package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.LoginUserRequest;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.models.client.response.LoginUserResponse;
import com.qentelli.employeetrackingsystem.serviceImpl.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse<LoginUserResponse>> loginByUser(@Valid @RequestBody LoginUserRequest loginUserRequest) {
		LoginUserResponse loginuser = userService.loginByEmail(loginUserRequest);

		AuthResponse<LoginUserResponse> authResponse = new AuthResponse<>(
				HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS,
				LocalDateTime.now(),
				"Login successful",
				loginuser
		);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
}
