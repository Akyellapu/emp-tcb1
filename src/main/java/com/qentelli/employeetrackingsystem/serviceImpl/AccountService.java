package com.qentelli.employeetrackingsystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.exception.AccountNotFoundException;
import com.qentelli.employeetrackingsystem.models.client.request.AccountDetailsDto;
import com.qentelli.employeetrackingsystem.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	// CREATE
	public Account createAccount(AccountDetailsDto dto) {
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
				.orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
		return modelMapper.map(account, AccountDetailsDto.class);
	}

	// FULL UPDATE
	public Account updateAccount(Integer id, AccountDetailsDto dto) {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
		modelMapper.map(dto, existingAccount);
		
		existingAccount.setAccountId(dto.getAccountId());
		existingAccount.setAccountName(dto.getAccountName());
		existingAccount.setAccountStartDate(dto.getAccountStartDate());
		existingAccount.setAccountEndDate(dto.getAccountEndDate());
		existingAccount.setCreatedAt(dto.getCreatedAt());
		existingAccount.setCreatedBy(dto.getCreatedBy());
		existingAccount.setUpdatedAt(dto.getUpdatedAt());
		existingAccount.setUpdatedBy(dto.getUpdatedBy());
		
		return accountRepository.save(existingAccount);
	}

	// PARTIAL UPDATE
	public Account partialUpdateAccount(Integer id, AccountDetailsDto dto) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));

		if (dto.getAccountName() != null)
			account.setAccountName(dto.getAccountName());
		if (dto.getAccountStartDate() != null)
			account.setAccountStartDate(dto.getAccountStartDate());
		if (dto.getAccountEndDate() != null)
			account.setAccountEndDate(dto.getAccountEndDate());
		if (dto.getCreatedAt() != null)
			account.setCreatedAt(dto.getCreatedAt());
		if (dto.getCreatedBy() != null)
			account.setCreatedBy(dto.getCreatedBy());
		if (dto.getUpdatedAt() != null)
			account.setUpdatedAt(dto.getUpdatedAt());
		if (dto.getUpdatedBy() != null)
			account.setUpdatedBy(dto.getUpdatedBy());

		return accountRepository.save(account);
	}

	// SOFT DELETE
	public Account softDeleteAccount(Integer id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("Account not found"));
		account.setSoftDelete(true);
		return accountRepository.save(account);
	}

	// HARD DELETE
	public Account deleteAccount(Integer id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
		accountRepository.deleteById(id);
		return account;
	}
}