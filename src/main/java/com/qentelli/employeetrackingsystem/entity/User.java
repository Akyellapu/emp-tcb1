package com.qentelli.employeetrackingsystem.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "User_Data")
public class User implements UserDetails {

	public User(String userName, String password, String firstName, String lastName, String employeeId) {
		this.userName = userName;
		this.password = password;
		this.confirmPassword = password; // optional: store encoded version later
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeId = employeeId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;

	@Column(unique = true)
	private String employeeId;
	private String userName;
	private String password;
	private String confirmPassword;
	@Enumerated(EnumType.STRING)
	private Roles roles;



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles.name()));
	}

	@Override
	public String getUsername() {
		return userName;
	}

}
