package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.AccountDetailsDto;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.serviceImpl.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Account")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	private final AccountService accountService;
	private final ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<AuthResponse<AccountDetailsDto>> createAccount(@Valid @RequestBody AccountDetailsDto accountDto) {
		logger.info("Creating new account with name: {}", accountDto.getAccountName());
		Account newAccount = accountService.createAccount(accountDto);
		AccountDetailsDto responseDto = modelMapper.map(newAccount, AccountDetailsDto.class);

		logger.debug("Account created: {}", responseDto);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.CREATED.value(),
				RequestProcessStatus.SUCCESS, "Account created successfully");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<AuthResponse<List<AccountDetailsDto>>> getAllAccounts() {
		logger.info("Fetching all accounts");
		List<AccountDetailsDto> accounts = accountService.getAllAccounts();

		logger.debug("Accounts retrieved: {}", accounts.size());
		AuthResponse<List<AccountDetailsDto>> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, LocalDateTime.now(), "Accounts fetched successfully", accounts);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthResponse<AccountDetailsDto>> getAccountById(@PathVariable int id) {
		logger.info("Fetching account by ID: {}", id);
		AccountDetailsDto dto = accountService.getAccountById(id);

		logger.debug("Account fetched: {}", dto);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, LocalDateTime.now(), "Account fetched successfully", dto);

		return ResponseEntity.ok(response);
	}
	
	 @GetMapping("/search/name")
	    public ResponseEntity<AuthResponse<List<AccountDetailsDto>>> searchAccountByName(@RequestParam String name) {
	        logger.info("Searching for accounts with name: {}", name);

	        List<AccountDetailsDto> results = accountService.searchAccountsByName(name);

	        HttpStatus status = results.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
	        RequestProcessStatus processStatus = results.isEmpty() ? RequestProcessStatus.FAILURE : RequestProcessStatus.SUCCESS;
	        String message = results.isEmpty() ? "No matching accounts found" : "Accounts fetched successfully";

	        logger.debug("Accounts found: {}", results.size());
	        AuthResponse<List<AccountDetailsDto>> response = new AuthResponse<>(
	            status.value(), processStatus, LocalDateTime.now(), message, results
	        );

	        return ResponseEntity.status(status).body(response);
	    }


	@PutMapping("/{id}")
	public ResponseEntity<AuthResponse<AccountDetailsDto>> updateAccount(@PathVariable int id,
			@RequestBody AccountDetailsDto updatedDto) {
		logger.info("Updating account with ID: {}", id);
		Account updated = accountService.updateAccount(id, updatedDto);
		AccountDetailsDto responseDto = modelMapper.map(updated, AccountDetailsDto.class);

		logger.debug("Account updated: {}", responseDto);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, "Account updated successfully");

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AuthResponse<AccountDetailsDto>> partialUpdateAccount(@PathVariable int id,
			@RequestBody AccountDetailsDto partialDto) {
		logger.info("Partially updating account with ID: {}", id);
		Account updated = accountService.partialUpdateAccount(id, partialDto);
		AccountDetailsDto responseDto = modelMapper.map(updated, AccountDetailsDto.class);

		logger.debug("Account partially updated: {}", responseDto);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, "Account partially updated successfully");

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/soft/{id}")
	public ResponseEntity<AuthResponse<AccountDetailsDto>> softDeleteAccount(@PathVariable int id) {
		logger.info("Soft deleting account with ID: {}", id);
		accountService.softDeleteAccount(id);

		logger.debug("Account soft deleted with ID: {}", id);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, "Account soft deleted successfully");

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AuthResponse<AccountDetailsDto>> deleteAccount(@PathVariable int id) {
		logger.info("Permanently deleting account with ID: {}", id);
		accountService.deleteAccount(id);

		logger.debug("Account permanently deleted with ID: {}", id);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, "Account permanently deleted successfully");

		return ResponseEntity.ok(response);
	}
}