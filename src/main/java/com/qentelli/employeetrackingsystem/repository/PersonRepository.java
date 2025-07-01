package com.qentelli.employeetrackingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qentelli.employeetrackingsystem.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    // You can add custom query methods here if needed, like:
    // Optional<Person> findByEmail(String email);
}