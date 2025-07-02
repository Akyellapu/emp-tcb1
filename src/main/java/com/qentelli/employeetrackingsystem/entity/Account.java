package com.qentelli.employeetrackingsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int accountId;
	@Column(unique = true)
	private String accountName;
	private LocalDate accountStartDate;
	private LocalDate accountEndDate;
	private Boolean softDelete = false;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@CreatedBy
	private String createdBy;
	
	private LocalDateTime updatedAt;

	private String updatedBy;

	// Add this inside your Account class

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> projects = new ArrayList<>();	
	
}
