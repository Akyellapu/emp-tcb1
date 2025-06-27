package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qentelli.employeetrackingsystem.exception.DuplicateProjectException;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.ProjectDTO;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse2;
import com.qentelli.employeetrackingsystem.serviceImpl.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<AuthResponse<ProjectDTO>> createProject(@RequestBody ProjectDTO projectRequest)
			throws DuplicateProjectException {
		logger.info("Creating new project with name: {}", projectRequest.getProjectName());
		ProjectDTO createdProject = projectService.create(projectRequest);

		logger.debug("Project created: {}", createdProject);
		AuthResponse<ProjectDTO> response = new AuthResponse<>(HttpStatus.CREATED.value(), RequestProcessStatus.SUCCESS,
				LocalDateTime.now(), "Project created successfully", createdProject);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<AuthResponse<List<ProjectDTO>>> getAllProjects() {
		logger.info("Fetching all projects");
		List<ProjectDTO> projectList = projectService.getAll();

		logger.debug("Number of projects fetched: {}", projectList.size());
		AuthResponse<List<ProjectDTO>> response = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, LocalDateTime.now(), "Projects fetched successfully", projectList);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthResponse<ProjectDTO>> getProjectById(@PathVariable Integer id) {
		logger.info("Fetching project with ID: {}", id);
		ProjectDTO project = projectService.getById(id);

		logger.debug("Project fetched: {}", project);
		AuthResponse<ProjectDTO> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				LocalDateTime.now(), "Project fetched successfully", project);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AuthResponse<ProjectDTO>> updateProject(@PathVariable Integer id,
			@RequestBody ProjectDTO projectUpdate) {
		logger.info("Updating project with ID: {}", id);
		ProjectDTO updatedProject = projectService.update(id, projectUpdate);

		logger.debug("Project updated: {}", updatedProject);
		AuthResponse<ProjectDTO> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				LocalDateTime.now(), "Project updated successfully", updatedProject);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/partialUpdateProject/{id}")
	public ResponseEntity<AuthResponse<ProjectDTO>> partiallyUpdateProject(@PathVariable int id,
			@RequestBody ProjectDTO patchDto) {
		logger.info("Partially updating project with ID: {}", id);
		ProjectDTO partiallyUpdatedProject = projectService.partialUpdateProject(id, patchDto);

		logger.debug("Project partially updated: {}", partiallyUpdatedProject);
		AuthResponse<ProjectDTO> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				LocalDateTime.now(), "Project partially updated successfully", partiallyUpdatedProject);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AuthResponse2<ProjectDTO>> deleteProject(@PathVariable Integer id) {
		logger.info("Deleting project with ID: {}", id);
		projectService.delete(id);

		logger.debug("Project with ID {} deleted", id);
		AuthResponse2<ProjectDTO> response = new AuthResponse2<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				"Project deleted successfully");

		return ResponseEntity.ok(response);
	}
}