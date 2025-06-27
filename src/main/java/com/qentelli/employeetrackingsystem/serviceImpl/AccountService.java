package com.qentelli.employeetrackingsystem.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.entity.User;
import com.qentelli.employeetrackingsystem.exception.AccountNotFoundException;
import com.qentelli.employeetrackingsystem.exception.DuplicateAccountException;
import com.qentelli.employeetrackingsystem.models.client.request.AccountDetailsDto;
import com.qentelli.employeetrackingsystem.repository.AccountRepository;

@Service
public class AccountService {

	private static final String ACCOUNT_NOT_FOUND = "Account not found with id: ";
	private final AccountRepository accountRepository;
	private final ModelMapper modelMapper;

	public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
		this.accountRepository = accountRepository;
		this.modelMapper = modelMapper;
	}

	// CREATE
	public Account createAccount(AccountDetailsDto dto) {
		if (accountRepository.existsByAccountName(dto.getAccountName())) {
			throw new DuplicateAccountException("An account with this name already exists.");
		}
		Account account = modelMapper.map(dto, Account.class);
		return accountRepository.save(account);
	}

	// READ ALL
	public List<AccountDetailsDto> getAllAccounts() {
		return accountRepository.findAll().stream().map(account -> modelMapper.map(account, AccountDetailsDto.class))
				.toList();
	}

	// READ BY ID
	public AccountDetailsDto getAccountById(Integer id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + id));
		return modelMapper.map(account, AccountDetailsDto.class);
	}

	// FULL UPDATE
	public Account updateAccount(Integer id, AccountDetailsDto dto) {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + id));

		modelMapper.map(dto, existingAccount);
		existingAccount.setAccountName(dto.getAccountName());
		existingAccount.setAccountStartDate(dto.getAccountStartDate());
		existingAccount.setAccountEndDate(dto.getAccountEndDate());

		existingAccount.setUpdatedAt(LocalDateTime.now());
		existingAccount.setUpdatedBy(getAuthenticatedUserFullName());

		return accountRepository.save(existingAccount);
	}

	// PARTIAL UPDATE
	public Account partialUpdateAccount(Integer id, AccountDetailsDto dto) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + id));

		if (dto.getAccountName() != null)
			account.setAccountName(dto.getAccountName());
		if (dto.getAccountStartDate() != null)
			account.setAccountStartDate(dto.getAccountStartDate());
		if (dto.getAccountEndDate() != null)
			account.setAccountEndDate(dto.getAccountEndDate());

		account.setUpdatedAt(LocalDateTime.now());
		account.setUpdatedBy(getAuthenticatedUserFullName());

		return accountRepository.save(account);
	}

	// SOFT DELETE
	public Account softDeleteAccount(Integer id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + id));
		account.setSoftDelete(true);
		account.setUpdatedAt(LocalDateTime.now());
		account.setUpdatedBy(getAuthenticatedUserFullName());
		return accountRepository.save(account);
	}

	// HARD DELETE
	public Account deleteAccount(Integer id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + id));
		accountRepository.deleteById(id);
		return account;
	}

	// Extracted method for full name resolution
	private String getAuthenticatedUserFullName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User user) {
			return user.getFirstName() + " " + user.getLastName();
		}
		return "System"; // Fallback
	}
}