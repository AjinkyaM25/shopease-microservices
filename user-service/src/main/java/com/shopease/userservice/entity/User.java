package com.shopease.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="users")
@Entity
public class User {
	
	private static final boolean flase = false;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message ="Name is required")
	@Column(nullable= false)
	private  String name;
	
	@Email(message ="Email should be valid")
	@NotBlank(message ="Email is required")
	@Column(nullable = flase ,unique =true)
	private String email;
	
	@NotBlank(message ="password is required")
	@Column(nullable=false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.USER;
	
	public enum Role {
		USER , ADMIN ,HOD ,HR ,GM
	
	}
	
}
