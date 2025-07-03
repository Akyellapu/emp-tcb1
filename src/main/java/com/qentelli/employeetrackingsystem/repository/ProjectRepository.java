package com.qentelli.employeetrackingsystem.repository;

import com.qentelli.employeetrackingsystem.entity.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	boolean existsByProjectName(String projectName);
	
	List<Project> findByProjectNameIgnoreCase(String projectName);

}