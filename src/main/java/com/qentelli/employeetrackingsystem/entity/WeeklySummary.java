package com.qentelli.employeetrackingsystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
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
    
	@Column(name = "soft_delete")
	private Boolean softDelete = false;

	@CreatedDate
	private LocalDateTime createdAt;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	@LastModifiedBy
	private String updatedBy;
	
	
	private String weekRange;

	@ManyToMany
	@JoinTable(name = "weekly_summary_project", joinColumns = @JoinColumn(name = "week_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Project> listProject;

	
}
