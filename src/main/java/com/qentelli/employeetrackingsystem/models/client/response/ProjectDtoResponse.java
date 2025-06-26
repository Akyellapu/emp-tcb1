package com.qentelli.employeetrackingsystem.models.client.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.qentelli.employeetrackingsystem.entity.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDtoResponse {

	 private int projectId;
	    private String projectName;
	    private String location;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private boolean active;
	    private LocalDateTime createdAt;
	    private String createdBy;
	    private LocalDateTime updatedAt;
	    private String updatedBy;
}
