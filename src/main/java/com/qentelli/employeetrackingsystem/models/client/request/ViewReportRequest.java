package com.qentelli.employeetrackingsystem.models.client.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qentelli.employeetrackingsystem.entity.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ViewReportRequest {
	private int viewReportId;
	private int weekId;
	private int projectId;
	private Integer personId;
	private String taskName;
	private TaskStatus taskStatus;
	private List<String> summary;
	private List<String> keyAccomplishment;
	private List<String> comments;
	private LocalDate taskStartDate;
	private LocalDate taskEndDate;

}