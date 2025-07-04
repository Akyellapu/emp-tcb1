package com.qentelli.employeetrackingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.qentelli.employeetrackingsystem.entity.Roles;
import com.qentelli.employeetrackingsystem.exception.RequestProcessStatus;
import com.qentelli.employeetrackingsystem.models.client.request.PersonDTO;
import com.qentelli.employeetrackingsystem.models.client.response.AuthResponse;
import com.qentelli.employeetrackingsystem.serviceImpl.PersonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Person")
public class PersonController {

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

	private final PersonService personService;

	@PostMapping
	public ResponseEntity<AuthResponse<PersonDTO>> createPerson(@Valid @RequestBody PersonDTO personDto) {
		logger.info("Creating new person: {}", personDto.getFirstName());
		PersonDTO responseDto = personService.create(personDto);

		logger.debug("Person created: {}", responseDto);
		AuthResponse<PersonDTO> response = new AuthResponse<>(HttpStatus.CREATED.value(), RequestProcessStatus.SUCCESS,
				"Person created successfully");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/tag-projects")
    public ResponseEntity<AuthResponse<String>> tagProjectsToEmployee(
            @RequestParam String personId,
            @RequestBody List<String> projectIds) {

        logger.info("Tagging projects to person with ID: {}", personId);
        Integer numericId = extractNumericId(personId);
        List<Integer> numericProjectIds = projectIds.stream()
        	    .map(Integer::parseInt)
        	    .toList();
        personService.tagProjectsToEmployee(numericId, numericProjectIds);

        AuthResponse<String> response = new AuthResponse<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                LocalDateTime.now(),
                "Project(s) tagged successfully",
                "Projects tagged to person with ID: " + personId
        );

        return ResponseEntity.ok(response);
    }

	@GetMapping
	public ResponseEntity<AuthResponse<List<PersonDTO>>> getAllPersons() {
		logger.info("Fetching all persons");
		List<PersonDTO> persons = personService.getAllResponses();

		logger.debug("Persons fetched: {}", persons.size());
		AuthResponse<List<PersonDTO>> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				LocalDateTime.now(), "Persons fetched successfully", persons);

		return ResponseEntity.ok(response);
	}

	
	  @GetMapping("/{id}")
	    public ResponseEntity<AuthResponse<PersonDTO>> getPersonById(@PathVariable String id) {
	        logger.info("Fetching person by ID: {}", id);
	        Integer numericId = extractNumericId(id);
	        PersonDTO dto = personService.getByIdResponse(numericId);

	        logger.debug("Person fetched: {}", dto);
	        AuthResponse<PersonDTO> response = new AuthResponse<>(
	                HttpStatus.OK.value(),
	                RequestProcessStatus.SUCCESS,
	                LocalDateTime.now(),
	                "Person fetched successfully",
	                dto
	        );

	        return ResponseEntity.ok(response);
	    }
	  

	@GetMapping("/search/name")
	public ResponseEntity<AuthResponse<List<PersonDTO>>> searchByName(@RequestParam String name) {
		logger.info("Searching for person(s) by name: {}", name);
		List<PersonDTO> results = personService.searchByName(name);

		HttpStatus status = results.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
		RequestProcessStatus processStatus = results.isEmpty() ? RequestProcessStatus.FAILURE
				: RequestProcessStatus.SUCCESS;
		String message = results.isEmpty() ? "No matching persons found" : "Persons fetched successfully";

		logger.debug("Search result count: {}", results.size());
		AuthResponse<List<PersonDTO>> response = new AuthResponse<>(status.value(), processStatus, LocalDateTime.now(),
				message, results);

		return ResponseEntity.status(status).body(response);
	}

	@GetMapping("/role/{role}")
	public ResponseEntity<AuthResponse<List<PersonDTO>>> getPersonsByRole(@PathVariable String role) {
		logger.info("Fetching persons with role: {}", role);

		Roles parsedRole;
		try {
			parsedRole = Roles.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			logger.error("Invalid role provided: {}", role);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse<>(HttpStatus.BAD_REQUEST.value(),
					RequestProcessStatus.FAILURE, LocalDateTime.now(), "Invalid role: " + role, null));
		}

		List<PersonDTO> persons = personService.getByRoleResponse(parsedRole);

		logger.debug("Persons with role {} fetched: {}", role, persons.size());
		AuthResponse<List<PersonDTO>> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
				LocalDateTime.now(), "Persons fetched successfully", persons);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/project/{projectId}")
	public ResponseEntity<AuthResponse<List<PersonDTO>>> getPersonsByProject(@PathVariable Integer projectId) {
		logger.info("Fetching persons tagged to project ID: {}", projectId);

		if (!personService.isProjectExists(projectId)) {
			logger.warn("Project not found with ID: {}", projectId);
			AuthResponse<List<PersonDTO>> errorResponse = new AuthResponse<>(HttpStatus.NOT_FOUND.value(),
					RequestProcessStatus.FAILURE, LocalDateTime.now(), "Project with ID " + projectId + " not found",
					null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<PersonDTO> persons = personService.getPersonsByProjectId(projectId);

		logger.debug("Persons fetched for project ID {}: {}", projectId, persons.size());
		AuthResponse<List<PersonDTO>> successResponse = new AuthResponse<>(HttpStatus.OK.value(),
				RequestProcessStatus.SUCCESS, LocalDateTime.now(), "Persons fetched successfully", persons);

		return ResponseEntity.ok(successResponse);
	}

	
	  @PutMapping("/{id}")
	    public ResponseEntity<AuthResponse<PersonDTO>> updatePerson(@PathVariable String id,
	                                                                @RequestBody PersonDTO updatedDto) {
	        logger.info("Updating person with ID: {}", id);
	        Integer numericId = extractNumericId(id);
	        PersonDTO responseDto = personService.update(numericId, updatedDto);

	        logger.debug("Person updated: {}", responseDto);
	        AuthResponse<PersonDTO> response = new AuthResponse<>(HttpStatus.OK.value(), RequestProcessStatus.SUCCESS,
					"Person updated successfully");
	        return ResponseEntity.ok(response);
	    }
	
    @DeleteMapping("/{id}")
    public ResponseEntity<AuthResponse<Void>> deletePerson(@PathVariable String id) {
        logger.info("Deleting person with ID: {}", id);
        Integer numericId = extractNumericId(id);
        personService.deletePersonById(numericId);

        logger.debug("Person deleted: {}", numericId);
        AuthResponse<Void> response = new AuthResponse<>(
                HttpStatus.OK.value(),
                RequestProcessStatus.SUCCESS,
                "Person deleted successfully"
        );

        return ResponseEntity.ok(response);
    }
	
	
    private Integer extractNumericId(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Person ID cannot be null or blank");
        }

        input = input.trim();

        // Accept both formatted (EMP008) and plain numeric (8)
        if (input.matches("(EMP|MAN)\\d+")) {
            return Integer.parseInt(input.replaceAll("[^\\d]", ""));
        }

        if (input.matches("\\d+")) {
            return Integer.parseInt(input);
        }

        throw new IllegalArgumentException("Invalid ID format: " + input);
    }
    
    
    

}