package com.qentelli.employeetrackingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qentelli.employeetrackingsystem.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
	
	boolean existsByAccountName(String accountName);
	
	List<Account> findByAccountNameIgnoreCase(String accountName);


}
