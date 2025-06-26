package com.qentelli.employeetrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qentelli.employeetrackingsystem.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {}
