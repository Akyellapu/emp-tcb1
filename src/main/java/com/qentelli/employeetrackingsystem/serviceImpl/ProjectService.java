package com.qentelli.employeetrackingsystem.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.entity.Person;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.entity.User;
import com.qentelli.employeetrackingsystem.entity.WeeklySummary;
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

    private static final String PROJECT_NOT_FOUND = "Project not found";
    private static final String ACCOUNT_NOT_FOUND = "Account not found";

    private final ProjectRepository projectRepo;
    private final AccountRepository accountRepo;
    private final PersonRepository personRepository;
    private final WeeklySummaryRepository weeklySummaryRepository;
    private final ModelMapper modelMapper;

    public ProjectDTO create(ProjectDTO dto) throws DuplicateProjectException {
        if (projectRepo.existsByProjectName(dto.getProjectName())) {
            throw new DuplicateProjectException("A project with this name already exists.");
        }

        Account account = accountRepo.findById(dto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND));

        Project project = modelMapper.map(dto, Project.class);
        project.setAccount(account);

        Project saved = projectRepo.save(project);
        return convertToDTO(saved);
    }

    public ProjectDTO getById(Integer id) {
        Project project = projectRepo.findById(parseId(id))
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND + " with ID: " + id));

        return convertToDTO(project);
    }

    public List<ProjectDTO> getAll() {
        List<Project> projects = projectRepo.findAll();
        return projects.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ProjectDTO> searchProjectsByName(String name) {
        List<Project> projects = projectRepo.findByProjectNameIgnoreCase(name);
        return projects.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    public ProjectDTO update(Integer id, ProjectDTO dto) {
        Project project = projectRepo.findById(parseId(id))
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND + " with ID: " + id));

        if (dto.getProjectName() != null)
            project.setProjectName(dto.getProjectName());

        if (dto.getSoftDelete() != null)
            project.setSoftDelete(dto.getSoftDelete());

        if (dto.getAccountId() != null) {
            Account account = accountRepo.findById(dto.getAccountId())
                    .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + " with ID: " + dto.getAccountId()));
            project.setAccount(account);
        }

        project.setUpdatedAt(LocalDateTime.now());
        project.setUpdatedBy(getAuthenticatedUserFullName());

        Project saved = projectRepo.save(project);
        return convertToDTO(saved);
    }

    @Transactional
    public ProjectDTO partialUpdateProject(int id, ProjectDTO dto) {
        Project project = projectRepo.findById(parseId(id))
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND + " with ID: " + id));

        if (dto.getProjectName() != null)
            project.setProjectName(dto.getProjectName());

        if (dto.getSoftDelete() != null)
            project.setSoftDelete(dto.getSoftDelete());

        if (dto.getCreatedBy() != null)
            project.setCreatedBy(dto.getCreatedBy());

        if (dto.getUpdatedBy() != null)
            project.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getCreatedAt() != null)
            project.setCreatedAt(dto.getCreatedAt());

        if (dto.getUpdatedAt() != null)
            project.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getAccountId() != null) {
            Account account = accountRepo.findById(dto.getAccountId())
                    .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + " with ID: " + dto.getAccountId()));
            project.setAccount(account);
        }

        Project saved = projectRepo.save(project);
        return convertToDTO(saved);
    }

    @Transactional
    public void deleteProject(Integer projectId) {
        Project project = projectRepo.findById(parseId(projectId))
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND + " with ID: " + projectId));

        // 🔗 Remove project from associated persons
        List<Person> linkedPersons = personRepository.findByProjectsContaining(project);
        for (Person person : linkedPersons) {
            person.getProjects().remove(project);
        }
        personRepository.saveAll(linkedPersons);

        // 🔗 Remove project from associated weekly summaries
        List<WeeklySummary> summaries = weeklySummaryRepository.findByListProjectContaining(project);
        for (WeeklySummary summary : summaries) {
            summary.getListProject().remove(project);
        }
        weeklySummaryRepository.saveAll(summaries);

        // ✅ Now safe to delete
        projectRepo.delete(project);
    }

    @Transactional
    public Project softDeleteProject(int id) {
        Project project = projectRepo.findById(parseId(id))
                .orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND + " with ID: " + id));

        project.setSoftDelete(true);
        return projectRepo.save(project);
    }

    // 🧠 Authenticated user helper
    private String getAuthenticatedUserFullName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User user) {
            return user.getFirstName() + " " + user.getLastName();
        }
        return "System";
    }

    // 🧱 DTO conversion
    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = modelMapper.map(project, ProjectDTO.class);

        if (project.getProjectId() != null) {
            dto.setFormattedProjectId(String.format("PJT%03d", project.getProjectId()));
        }

        if (project.getAccount() != null) {
            dto.setAccountName(project.getAccount().getAccountName());
        }

        return dto;
    }

    // 🧪 Robust ID parser
    private Integer parseId(Object input) {
        if (input instanceof String str && str.matches("PJT\\d+")) {
            return Integer.parseInt(str.replaceAll("[^\\d]", ""));
        } else if (input instanceof Integer num) {
            return num;
        }
        throw new IllegalArgumentException("Invalid project ID format: " + input);
    }
}