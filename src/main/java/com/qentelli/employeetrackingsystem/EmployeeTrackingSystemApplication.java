package com.qentelli.employeetrackingsystem;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.qentelli.employeetrackingsystem.entity.Roles;
import com.qentelli.employeetrackingsystem.entity.User;
import com.qentelli.employeetrackingsystem.repository.UserRepository;

@SpringBootApplication
public class EmployeeTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeTrackingSystemApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner seedAdmins(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	    return args -> {
	        List<User> admins = List.of(
	            new User("superadmin@gmail.com", "Sarath11@", "Sarath", "Sarath", "EMP1001"),
	            new User("superadmin2@gmail.com", "Madhu123@", "Madhu", "Marrapu", "EMP1002")
	        );

	        for (User adminSeed : admins) {
	            if (userRepository.findByUserName(adminSeed.getUsername()).isEmpty()) {
	                User admin = new User();
	                admin.setUserName(adminSeed.getUsername());
	                admin.setPassword(passwordEncoder.encode(adminSeed.getPassword()));
	                admin.setConfirmPassword(passwordEncoder.encode(adminSeed.getPassword()));
	                admin.setFirstName(adminSeed.getFirstName());
	                admin.setLastName(adminSeed.getLastName());
	                admin.setEmployeeId(adminSeed.getEmployeeId());
	                admin.setRoles(Roles.SUPERADMIN);
	                userRepository.save(admin);
	            }
	        }
	    };
	}
}
