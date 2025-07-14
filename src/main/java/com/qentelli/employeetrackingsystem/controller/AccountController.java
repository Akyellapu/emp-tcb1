package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.qentelli.employeetrackingsystem.models.client.response.PaginatedResponse;
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
	public ResponseEntity<AuthResponse<AccountDetailsDto>> createAccount(
			@Valid @RequestBody AccountDetailsDto accountDto) {
		logger.info("Creating new account with name: {}", accountDto.getAccountName());
		Account newAccount = accountService.createAccount(accountDto);
		AccountDetailsDto responseDto = modelMapper.map(newAccount, AccountDetailsDto.class);

		logger.debug("Account created: {}", responseDto);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.CREATED.value(),
				RequestProcessStatus.SUCCESS, "Account created successfully");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<AuthResponse<PaginatedResponse<AccountDetailsDto>>> getAllActiveAccountsPaginated(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "accountName") String sortBy
	) {
	    logger.info("Fetching paginated list of active accounts: page={}, size={}, sortBy={}", page, size, sortBy);

	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<AccountDetailsDto> accountPage = accountService.getAllActiveAccounts(pageable);

	    PaginatedResponse<AccountDetailsDto> paginated = new PaginatedResponse<>(
	            accountPage.getContent(),
	            accountPage.getNumber(),
	            accountPage.getSize(),
	            accountPage.getTotalElements(),
	            accountPage.getTotalPages(),
	            accountPage.isLast()
	    );

	    logger.debug("Paginated accounts fetched: count={}, totalPages={}",
	                 accountPage.getNumberOfElements(), accountPage.getTotalPages());

	    AuthResponse<PaginatedResponse<AccountDetailsDto>> response = new AuthResponse<>(
	            HttpStatus.OK.value(),
	            RequestProcessStatus.SUCCESS,
	            LocalDateTime.now(),
	            "Paginated active accounts fetched successfully",
	            paginated
	    );

	    return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<AuthResponse<PaginatedResponse<AccountDetailsDto>>> searchAccountsByNamePaginated(
	        @RequestParam String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "accountName") String sortBy
	) {
	    logger.info("Searching accounts by name (case-insensitive): name={}, page={}, size={}, sortBy={}", name, page, size, sortBy);

	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<Account> accountPage = accountService.searchAccountsByExactName(name, pageable);

	    List<AccountDetailsDto> dtoList = accountPage.getContent().stream()
	            .map(account -> modelMapper.map(account, AccountDetailsDto.class))
	            .toList();

	    PaginatedResponse<AccountDetailsDto> paginated = new PaginatedResponse<>(
	            dtoList,
	            accountPage.getNumber(),
	            accountPage.getSize(),
	            accountPage.getTotalElements(),
	            accountPage.getTotalPages(),
	            accountPage.isLast()
	    );

	    logger.debug("Search results fetched: matchCount={}, totalPages={}",
	            accountPage.getNumberOfElements(), accountPage.getTotalPages());

	    AuthResponse<PaginatedResponse<AccountDetailsDto>> response = new AuthResponse<>(
	            HttpStatus.OK.value(),
	            RequestProcessStatus.SUCCESS,
	            LocalDateTime.now(),
	            "Accounts fetched successfully",
	            paginated
	    );

	    return ResponseEntity.ok(response);
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
	@DeleteMapping("/{id}")
	public ResponseEntity<AuthResponse<AccountDetailsDto>> deleteAccount(@PathVariable int id) {
		logger.info("Permanently deleting account with ID: {}", id);
		accountService.deleteAccount(id);

		logger.debug("Account permanently deleted with ID: {}", id);
		AuthResponse<AccountDetailsDto> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, "Account temporarily deactivated ");
        // Note: This is a soft delete, not a permanent deletion
		return ResponseEntity.ok(response);
	}
}