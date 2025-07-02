package com.qentelli.employeetrackingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qentelli.employeetrackingsystem.entity.Person;
import com.qentelli.employeetrackingsystem.entity.Project;
import com.qentelli.employeetrackingsystem.entity.Roles;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	// You can add custom query methods here if needed, like:
	// Optional<Person> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByEmployeeCode(String employeeCode);

	List<Person> findByRole(Roles role);
	
	List<Person> findByProjectsContaining(Project project);

}