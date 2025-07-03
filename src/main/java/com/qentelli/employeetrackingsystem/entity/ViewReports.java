package com.qentelli.employeetrackingsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ViewReports")
@EntityListeners(AuditingEntityListener.class)
public class ViewReports {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer viewReportId;

	@Embedded
	private Task task;

	@Enumerated(EnumType.STRING)
	private TaskStatus taskStatus;

	@ElementCollection
	private List<String> comments;

	@ManyToOne
	@JoinColumn(name = "week_id")
	private WeeklySummary weeklySummary;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "personId")
	private Person person;
	
	//private User user;
	
	private String taskName;
	private LocalDate taskStartDate;
	private LocalDate taskEndDate;
	private Boolean softDelete = false;
	@CreatedDate
	private LocalDateTime createdAt;
	@CreatedBy
	private String createdBy;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@LastModifiedBy
	private String updatedBy;
}
