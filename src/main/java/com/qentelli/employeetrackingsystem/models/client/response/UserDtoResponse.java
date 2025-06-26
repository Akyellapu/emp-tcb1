package com.qentelli.employeetrackingsystem.models.client.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
	private int id;
	private String firstName;
	private String lastName;
	private String employeeId;
	private String email;
	private String roles;
	private String techStack;
	private List<ProjectDtoResponse> projects;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;

}
