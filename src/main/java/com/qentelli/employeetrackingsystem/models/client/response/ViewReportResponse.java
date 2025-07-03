package com.qentelli.employeetrackingsystem.models.client.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.qentelli.employeetrackingsystem.entity.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewReportResponse {
	private int viewReportId;
	private String taskName;
	private TaskStatus taskStatus;
	private List<String> summary;
	private List<String> keyAccomplishment;
	private List<String> comments;
	private String projectName;
	private String personName;
	private LocalDate taskStartDate;
	private LocalDate taskEndDate;
	private LocalDateTime createdAt;
	private String createdBy;
}
