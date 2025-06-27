package com.qentelli.employeetrackingsystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qentelli.employeetrackingsystem.entity.Manager;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.exception.ManagerNotFoundException;
import com.qentelli.employeetrackingsystem.models.client.request.ManagerDTO;
import com.qentelli.employeetrackingsystem.repository.ManagerRepository;
import com.qentelli.employeetrackingsystem.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {

	private static final String MANAGER_NOT_FOUND = "Manager not found";

	private final ManagerRepository managerRepo;
	private final ProjectRepository projectRepo;
	private final ModelMapper modelMapper;

	public ManagerDTO create(ManagerDTO dto) {
		Manager manager = modelMapper.map(dto, Manager.class);

		if (dto.getProjectIds() != null) {
			List<Project> projects = projectRepo.findAllById(dto.getProjectIds());
			manager.setProjects(projects);
		}

		Manager saved = managerRepo.save(manager);
		return modelMapper.map(saved, ManagerDTO.class);
	}

	public ManagerDTO getById(Integer id) {
		Manager manager = managerRepo.findById(id).orElseThrow(() -> new ManagerNotFoundException(MANAGER_NOT_FOUND));
		return modelMapper.map(manager, ManagerDTO.class);
	}

	public List<ManagerDTO> getAll() {
		return managerRepo.findAll().stream().map(m -> modelMapper.map(m, ManagerDTO.class)).toList();
	}

	@Transactional
	public ManagerDTO update(Integer id, ManagerDTO dto) {
		Manager manager = managerRepo.findById(id).orElseThrow(() -> new ManagerNotFoundException(MANAGER_NOT_FOUND));

		manager.setFirstName(dto.getFirstName());
		manager.setLastName(dto.getLastName());
		manager.setEmail(dto.getEmail());
		manager.setEmployeeCode(dto.getEmployeeCode());
		manager.setPassword(dto.getPassword());
		manager.setConfirmPassword(dto.getConfirmPassword());
		manager.setRole(dto.getRole());
		manager.setTechStack(dto.getTechStack());

		if (dto.getProjectIds() != null) {
			List<Project> projects = projectRepo.findAllById(dto.getProjectIds());
			manager.setProjects(projects);
		}

		return modelMapper.map(manager, ManagerDTO.class);
	}

	public void delete(Integer id) {
		Manager manager = managerRepo.findById(id).orElseThrow(() -> new ManagerNotFoundException(MANAGER_NOT_FOUND));
		managerRepo.delete(manager);
	}
}