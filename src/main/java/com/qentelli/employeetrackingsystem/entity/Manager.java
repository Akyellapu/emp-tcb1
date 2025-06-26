package com.qentelli.employeetrackingsystem.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer managerId;
    
    private String firstName;
    private String lastName;
    private String email;
    
    private String employeeCode;
    private String password;
    private String confirmPassword;
    
    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "manager_id") // FK will be added in Project table
    private List<Project> projects = new ArrayList<>();

    @ElementCollection(targetClass = TechStack.class)
    @Enumerated(EnumType.STRING)
    private List<TechStack> techStack = new ArrayList<>();
}