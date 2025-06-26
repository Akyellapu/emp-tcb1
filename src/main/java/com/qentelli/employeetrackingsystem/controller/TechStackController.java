package com.qentelli.employeetrackingsystem.controller;

import com.qentelli.employeetrackingsystem.entity.TechStack;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tech-stacks")
public class TechStackController {
	
	/*
	 * its for drop down for selecting tech stack while creating the manager
	 */
    @GetMapping
    public TechStack[] getAllTechStacks() {
        return TechStack.values();
    }
}