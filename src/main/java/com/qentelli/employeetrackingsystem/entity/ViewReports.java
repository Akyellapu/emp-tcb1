package com.qentelli.employeetrackingsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
public class ViewReports {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int viewReportId;

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
	@JoinColumn(name = "employee_id")
	private User user;
	private String taskName;
	private LocalDate taskStartDate;
	private LocalDate taskEndDate;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;
}
