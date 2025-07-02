package com.qentelli.employeetrackingsystem.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TechStack {
 FRONTEND,
	BACKEND,
	FULLSTACK,
	TESTING;
	
	 @JsonValue
	    public String getDisplayName() {
	        return name().charAt(0) + name().substring(1).toLowerCase();
	    }

}