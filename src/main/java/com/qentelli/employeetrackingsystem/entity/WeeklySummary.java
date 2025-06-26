package com.qentelli.employeetrackingsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WeeklySummary")
public class WeeklySummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int weekId;
	private LocalDate weekStartDate;
	private LocalDate weekEndDate;

	@ElementCollection
	@CollectionTable(name = "upcoming_tasks", joinColumns = @JoinColumn(name = "week_id"))
	@Column(name = "task")
	private List<String> upcomingTasks;

	private boolean status;

	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;

	@ManyToMany
	@JoinTable(name = "weekly_summary_project", joinColumns = @JoinColumn(name = "week_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Project> listProject;

	
}

