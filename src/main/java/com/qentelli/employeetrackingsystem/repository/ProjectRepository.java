package com.qentelli.employeetrackingsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qentelli.employeetrackingsystem.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	boolean existsByProjectName(String projectName);

	Page<Project> findByProjectNameContainingIgnoreCase(String projectName, Pageable pageable);

	Page<Project> findByProjectStatusTrue(Pageable pageable);

}