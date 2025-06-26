package com.qentelli.employeetrackingsystem.models.client.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qentelli.employeetrackingsystem.entity.Roles;
import com.qentelli.employeetrackingsystem.entity.TechStack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ManagerDTO {
    private Integer managerId;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeCode;
    private String password;
    private String confirmPassword;
    private Roles role;
    private List<TechStack> techStack;
    private List<Integer> projectIds;
}