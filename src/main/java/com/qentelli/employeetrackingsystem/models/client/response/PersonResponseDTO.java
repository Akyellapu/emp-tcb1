package com.qentelli.employeetrackingsystem.models.client.response;

import com.qentelli.employeetrackingsystem.entity.Roles;
import com.qentelli.employeetrackingsystem.entity.TechStack;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {
    private Integer personId;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeCode;
    private Roles role;
    private TechStack techStack;
    private List<String> projectNames;
}