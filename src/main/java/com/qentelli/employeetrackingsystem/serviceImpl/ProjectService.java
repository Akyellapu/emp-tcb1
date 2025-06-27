package com.qentelli.employeetrackingsystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qentelli.employeetrackingsystem.entity.Account;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.exception.AccountNotFoundException;
import com.qentelli.employeetrackingsystem.models.client.request.ProjectDTO;
import com.qentelli.employeetrackingsystem.repository.AccountRepository;
import com.qentelli.employeetrackingsystem.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
	
	private static final String PROJECT_NOT_FOUND = "Project not found";
	private static final String ACCOUNT_NOT_FOUND = "Account not found";

    private final ProjectRepository projectRepo;
    private final AccountRepository accountRepo;
    private final ModelMapper modelMapper;

   
    public ProjectDTO create(ProjectDTO dto) {
        Project project = modelMapper.map(dto, Project.class);
        Account account = accountRepo.findById(dto.getAccountId())
            .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND));

        project.setAccount(account);
        Project saved = projectRepo.save(project);
        return modelMapper.map(saved, ProjectDTO.class);
    }

 
    public ProjectDTO getById(Integer id) {
        Project project = projectRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        return modelMapper.map(project, ProjectDTO.class);
    }

   
    public List<ProjectDTO> getAll() {
        return projectRepo.findAll().stream()
            .map(p -> modelMapper.map(p, ProjectDTO.class))
            .toList();
    }

 
    @Transactional
    public ProjectDTO update(Integer id, ProjectDTO dto) {
        Project project = projectRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setProjectName(dto.getProjectName());
        project.setUpdatedAt(dto.getUpdatedAt());
        project.setUpdatedBy(dto.getUpdatedBy());
        project.setSoftDelete(dto.getSoftDelete());

        return modelMapper.map(project, ProjectDTO.class);
    }


    public void delete(Integer id) {
        Project project = projectRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepo.delete(project);
    }
}