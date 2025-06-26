package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.AccountDetailsDto;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse2;
import com.qentelli.employeetrackingsystem.serviceImpl.AccountService;

@RestController
@RequestMapping("/Account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<?> createAccount(@RequestBody AccountDetailsDto dto) {
        Account created = accountService.createAccount(dto);
        AccountDetailsDto mapped = modelMapper.map(created, AccountDetailsDto.class);

        AuthResponse2<AccountDetailsDto> response = new AuthResponse2<>(
                HttpStatus.CREATED.value(),
                RequestProcessStatus.SUCCESS,
                "Account created successfully"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<AuthResponse<List<AccountDetailsDto>>> getAllAccounts() {
        List<AccountDetailsDto> list = accountService.getAllAccounts();

        AuthResponse<List<AccountDetailsDto>> response = new AuthResponse<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                LocalDateTime.now(),
                "Accounts fetched successfully",
                list
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthResponse<AccountDetailsDto>> getAccountById(@PathVariable int id) {
        AccountDetailsDto dto = accountService.getAccountById(id);

        AuthResponse<AccountDetailsDto> response = new AuthResponse<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                LocalDateTime.now(),
                "Account fetched successfully",
                dto
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateAccount(@PathVariable int id, @RequestBody AccountDetailsDto dto) {
        Account updated = accountService.updateAccount(id, dto);
        AccountDetailsDto mapped = modelMapper.map(updated, AccountDetailsDto.class);

        AuthResponse2<AccountDetailsDto> response = new AuthResponse2<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                "Account updated successfully"
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> partialUpdateAccount(@PathVariable int id, @RequestBody AccountDetailsDto dto) {
        Account updated = accountService.partialUpdateAccount(id, dto);
        AccountDetailsDto mapped = modelMapper.map(updated, AccountDetailsDto.class);

        AuthResponse2<AccountDetailsDto> response = new AuthResponse2<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                "Account partially updated successfully"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<?> softDeleteAccount(@PathVariable int id) {
        accountService.softDeleteAccount(id);

        AuthResponse2<AccountDetailsDto> response = new AuthResponse2<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                "Account soft deleted successfully"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);

        AuthResponse2<AccountDetailsDto> response = new AuthResponse2<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                "Account permanently deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
}
