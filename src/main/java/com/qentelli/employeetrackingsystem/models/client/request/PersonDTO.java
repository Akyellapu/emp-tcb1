package com.qentelli.employeetrackingsystem.models.client.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.qentelli.employeetrackingsystem.config.FlexibleTechStackDeserializer;
import com.qentelli.employeetrackingsystem.entity.Roles;
import com.qentelli.employeetrackingsystem.entity.TechStack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PersonDTO {
    private Integer personId;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeCode;
    private String password;
    private String confirmPassword;
    private Roles role;
    private Boolean personStatus=true; // true for active, false for inactive;
    @JsonDeserialize(using = FlexibleTechStackDeserializer.class)
    private TechStack techStack;
    
    private List<Integer> projectIds;   // Existing mapping by ID
    private List<String> projectNames;  // New field for project name mapping
}