package com.qentelli.employeetrackingsystem.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Task {

	@ElementCollection
	private List<String> summary;
	@ElementCollection
	private List<String> keyAccomplishment;

}
