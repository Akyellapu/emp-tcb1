package com.qentelli.employeetrackingsystem.models.client.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProjectDTO {
	private Integer projectId;

	@NotBlank(message = "Project name is required")
	@Size(max = 50, message = "Project name must not exceed 20 characters")
	private String projectName;
    private Boolean projectStatus= true; // true means active, false means inactive;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;
	@NotNull(message = "Account ID is required")
	private Integer accountId;
	private String accountName;
}