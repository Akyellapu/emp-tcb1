package com.qentelli.employeetrackingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qentelli.employeetrackingsystem.entity.Person;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.entity.Roles;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

	boolean existsByEmail(String email);

	boolean existsByEmployeeCode(String employeeCode);

	List<Person> findByRole(Roles role);
	
	List<Person> findByProjectsContaining(Project project);
	
	Optional<Person> findByPersonId(Integer personId);
	
	List<Person> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
	 

}