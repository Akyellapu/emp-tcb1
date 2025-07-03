package com.qentelli.employeetrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qentelli.employeetrackingsystem.entity.ViewReports;

@Repository
public interface ViewreportRepository extends JpaRepository<ViewReports, Integer>{

}
