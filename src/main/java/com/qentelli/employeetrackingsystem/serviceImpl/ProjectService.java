package com.qentelli.employeetrackingsystem.serviceImpl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.entity.User;
import com.qentelli.employeetrackingsystem.exception.AccountNotFoundException;
import com.qentelli.employeetrackingsystem.exception.DuplicateProjectException;
import com.qentelli.employeetrackingsystem.exception.ProjectNotFoundException;
import com.qentelli.employeetrackingsystem.models.client.request.ProjectDTO;
import com.qentelli.employeetrackingsystem.repository.AccountRepository;
import com.qentelli.employeetrackingsystem.repository.PersonRepository;
import com.qentelli.employeetrackingsystem.repository.ProjectRepository;
import com.qentelli.employeetrackingsystem.repository.WeeklySummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private static final String PROJECT_NOT_FOUND = "Project not found with id : ";
	private static final String ACCOUNT_NOT_FOUND = "Account not found with id : ";

	private final ProjectRepository projectRepo;
	private final AccountRepository accountRepo;
	private final PersonRepository personRepository;
	private final WeeklySummaryRepository weeklySummaryRepo;
	private final ModelMapper modelMapper;

	public ProjectDTO create(ProjectDTO dto) throws DuplicateProjectException {
		if (projectRepo.existsByProjectName(dto.getProjectName())) {
			throw new DuplicateProjectException("A project with this name already exists.");
		}
		Account account = accountRepo.findById(dto.getAccountId())
				.orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + dto.getAccountId()));

		Project project = modelMapper.map(dto, Project.class);
		project.setAccount(account);
		Project saved = projectRepo.save(project);
		return modelMapper.map(saved, ProjectDTO.class);
	}

//	public ProjectDTO getById(Integer id) {
//	    Project project = projectRepo.findById(id)
//	        .orElseThrow(() -> new RuntimeException(PROJECT_NOT_FOUND + id));
//
//	    ProjectDTO dto = modelMapper.map(project, ProjectDTO.class);
//	    if (project.getAccount() != null) {
//	        dto.setAccountName(project.getAccount().getAccountName());
//	    }
//
//	    return dto;
//	}
//
//	
//	public List<ProjectDTO> getAll() {
//	    List<Project> projects = projectRepo.findAll();
//
//	    return projects.stream().map(project -> {
//	        ProjectDTO dto = modelMapper.map(project, ProjectDTO.class);
//	        dto.setAccountName(project.getAccount().getAccountName());
//	        return dto;
//	    }).toList();
//	}
//	

	@Transactional
	public ProjectDTO update(Integer id, ProjectDTO dto) {
		Project project = projectRepo.findById(id).orElseThrow(() -> new RuntimeException(PROJECT_NOT_FOUND + id));
		project.setProjectName(dto.getProjectName());
		//project.setProjectStatus(dto.getProjectStatus());
		project.setUpdatedAt(LocalDateTime.now());
		project.setUpdatedBy(getAuthenticatedUserFullName());

		return modelMapper.map(project, ProjectDTO.class);
	}

//	@Transactional
//	public ProjectDTO partialUpdateProject(int id, ProjectDTO dto) {
//		Project project = projectRepo.findById(id)
//				.orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND  + id));
//
//		if (dto.getProjectName() != null)
//			project.setProjectName(dto.getProjectName());
//		if (dto.getCreatedBy() != null)
//			project.setCreatedBy(dto.getCreatedBy());
//		if (dto.getUpdatedBy() != null)
//			project.setUpdatedBy(dto.getUpdatedBy());
//		if (dto.getCreatedAt() != null)
//			project.setCreatedAt(dto.getCreatedAt());
//		if (dto.getUpdatedAt() != null)
//			project.setUpdatedAt(dto.getUpdatedAt());
//
//		if (dto.getAccountId() != null) {
//			Account account = accountRepo.findById(dto.getAccountId()).orElseThrow(
//					() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + dto.getAccountId()));
//			project.setAccount(account);
//		}
//
//		Project saved = projectRepo.save(project);
//		return modelMapper.map(saved, ProjectDTO.class);
//	}

	@Transactional
	public void deleteProject(Integer projectId) {
		// Step 1: Fetch the managed project entity
		Project project = projectRepo.findById(projectId)
				.orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND + projectId));

		// Step 2: Soft delete the project (mark inactive)
		project.setProjectStatus(false);
		project.setUpdatedAt(LocalDateTime.now());
		project.setUpdatedBy(getAuthenticatedUserFullName());

		// Step 3: Save the updated project
		projectRepo.save(project);

	}

//	public Project softDeleteProject(int id) {
//        Project project = projectRepo.findById(id)
//                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND  + id));
//        project.setProjectStatus(true);
//        return projectRepo.save(project);
//    }

	public Page<Project> searchProjectsByExactName(String name, Pageable pageable) {
		return projectRepo.findByProjectNameContainingIgnoreCase(name, pageable);
	}

	public Page<Project> getactiveProjects(Pageable pageable) {
		return projectRepo.findByProjectStatusTrue(pageable);
	}

	private String getAuthenticatedUserFullName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User user) {
			return user.getFirstName() + " " + user.getLastName();
		}
		return "System";
	}
}