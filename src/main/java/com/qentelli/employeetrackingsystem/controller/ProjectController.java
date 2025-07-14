package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.exception.DuplicateProjectException;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.ProjectDTO;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.models.client.response.PaginatedResponse;
import com.qentelli.employeetrackingsystem.serviceImpl.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	private final ProjectService projectService;
	private final ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<AuthResponse<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO projectRequest)
			throws DuplicateProjectException {
		logger.info("Creating new project with name: {}", projectRequest.getProjectName());
		ProjectDTO createdProject = projectService.create(projectRequest);

		logger.debug("Project created: {}", createdProject);
		AuthResponse<ProjectDTO> response = new AuthResponse<>(HttpStatus.CREATED.value(), RequestProcessStatus.SUCCESS,
				"Project created successfully");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	@GetMapping("/search")
	public ResponseEntity<AuthResponse<PaginatedResponse<ProjectDTO>>> searchProjectsByNamePaginated(
	        @RequestParam String name,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "projectName") String sortBy
	) {
	    logger.info("Searching projects by name (case-insensitive): name={}, page={}, size={}, sortBy={}", name, page, size, sortBy);

	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<Project> projectPage = projectService.searchProjectsByExactName(name, pageable);

	    List<ProjectDTO> dtoList = projectPage.getContent().stream()
	            .map(project -> modelMapper.map(project, ProjectDTO.class))
	            .toList();

	    PaginatedResponse<ProjectDTO> paginated = new PaginatedResponse<>(
	            dtoList,
	            projectPage.getNumber(),
	            projectPage.getSize(),
	            projectPage.getTotalElements(),
	            projectPage.getTotalPages(),
	            projectPage.isLast()
	    );

	    logger.debug("Search results fetched: matchCount={}, totalPages={}",
	                 projectPage.getNumberOfElements(), projectPage.getTotalPages());

	    AuthResponse<PaginatedResponse<ProjectDTO>> response = new AuthResponse<>(
	            HttpStatus.OK.value(),
	            RequestProcessStatus.SUCCESS,
	            LocalDateTime.now(),
	            "Projects fetched successfully",
	            paginated
	    );

	    return ResponseEntity.ok(response);
	}

	@GetMapping()
	public ResponseEntity<AuthResponse<PaginatedResponse<ProjectDTO>>> getActiveProjectsPaginated(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "projectName") String sortBy
	) {
	    logger.info("Fetching paginated list of active projects: page={}, size={}, sortBy={}", page, size, sortBy);

	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<Project> projectPage = projectService.getactiveProjects(pageable);

	    List<ProjectDTO> dtoList = projectPage.getContent().stream()
	            .map(project -> modelMapper.map(project, ProjectDTO.class))
	            .toList();

	    PaginatedResponse<ProjectDTO> paginated = new PaginatedResponse<>(
	            dtoList,
	            projectPage.getNumber(),
	            projectPage.getSize(),
	            projectPage.getTotalElements(),
	            projectPage.getTotalPages(),
	            projectPage.isLast()
	    );

	    logger.debug("Active projects fetched: count={}, totalPages={}",
	                 projectPage.getNumberOfElements(), projectPage.getTotalPages());

	    AuthResponse<PaginatedResponse<ProjectDTO>> response = new AuthResponse<>(
	            HttpStatus.OK.value(),
	            RequestProcessStatus.SUCCESS,
	            LocalDateTime.now(),
	            "Active projects fetched successfully",
	            paginated
	    );

	    return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AuthResponse<ProjectDTO>> updateProject(@PathVariable Integer id,
			@RequestBody ProjectDTO projectUpdate) {
		logger.info("Updating project with ID: {}", id);
		ProjectDTO updatedProject = projectService.update(id, projectUpdate);

		logger.debug("Project updated: {}", updatedProject);
		AuthResponse<ProjectDTO> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				"Project updated successfully");

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AuthResponse<ProjectDTO>> deleteProject(@PathVariable Integer id) {
		logger.info("Deleting project with ID: {}", id);
		projectService.deleteProject(id);

		logger.debug("Project with ID {} deleted", id);
		AuthResponse<ProjectDTO> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				"Project temporarily deactivated");

		return ResponseEntity.ok(response);
	}
}