package com.qentelli.employeetrackingsystem.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qentelli.employeetrackingsystem.entity.Person;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.entity.Roles;
import com.qentelli.employeetrackingsystem.exception.DuplicatePersonException;
import com.qentelli.employeetrackingsystem.exception.PersonNotFoundException;
import com.qentelli.employeetrackingsystem.models.client.request.PersonDTO;
import com.qentelli.employeetrackingsystem.repository.PersonRepository;
import com.qentelli.employeetrackingsystem.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

	private static final String PERSON_NOT_FOUND = "Person not found";

	private final PersonRepository personRepo;
	private final ProjectRepository projectRepo;
	private final ModelMapper modelMapper;

	public PersonDTO create(PersonDTO dto) {

		boolean exists = personRepo.existsByEmail(dto.getEmail())
				|| personRepo.existsByEmployeeCode(dto.getEmployeeCode());

		if (exists) {
			throw new DuplicatePersonException("Person with this email or employee code already exists");
		}

		Person person = modelMapper.map(dto, Person.class);

		if (dto.getProjectIds() != null) {
			List<Project> projects = projectRepo.findAllById(dto.getProjectIds());
			person.setProjects(projects);
		}

		Person saved = personRepo.save(person);
		return convertToDTO(saved);
	}

	public PersonDTO getById(Integer id) {
		Person person = personRepo.findById(id).orElseThrow(() -> new PersonNotFoundException(PERSON_NOT_FOUND));
		return convertToDTO(person);
	}

	public List<PersonDTO> getAll() {
		return personRepo.findAll().stream().map(this::convertToDTO).toList();
	}

	public List<PersonDTO> getByRole(Roles role) {
		List<Person> persons = personRepo.findByRole(role);
		return persons.stream().map(this::convertToDTO).toList();
	}

	@Transactional
	public PersonDTO update(Integer id, PersonDTO dto) {
		Person person = personRepo.findById(id).orElseThrow(() -> new PersonNotFoundException(PERSON_NOT_FOUND + "with id : "+id));

		person.setFirstName(dto.getFirstName());
		person.setLastName(dto.getLastName());
		person.setEmail(dto.getEmail());
		person.setEmployeeCode(dto.getEmployeeCode());
		person.setPassword(dto.getPassword());
		person.setConfirmPassword(dto.getConfirmPassword());
		person.setRole(dto.getRole());
		person.setTechStack(dto.getTechStack());

		if (dto.getProjectIds() != null) {
			List<Project> projects = projectRepo.findAllById(dto.getProjectIds());
			person.setProjects(projects);
		}

		return convertToDTO(person);
	}

	@Transactional
	public void deletePersonById(Integer personId) {
		Person person = personRepo.findById(personId)
				.orElseThrow(() -> new PersonNotFoundException(PERSON_NOT_FOUND + "with id :" + personId));

		// Unlink projects
		person.getProjects().clear();

		// You can also optionally clear techStack if needed:
		person.getTechStack().clear();

		personRepo.delete(person);
	}

	private PersonDTO convertToDTO(Person person) {
		PersonDTO dto = modelMapper.map(person, PersonDTO.class);

		List<Project> projects = person.getProjects();
		if (projects != null) {
			dto.setProjectIds(projects.stream().map(Project::getProjectId).toList());

			dto.setProjectNames(projects.stream().map(Project::getProjectName).toList());
		}

		return dto;
	}
}