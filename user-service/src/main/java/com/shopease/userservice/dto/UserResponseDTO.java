package com.shopease.userservice.dto;

import com.shopease.userservice.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	
	private Long id ;
	private String name;
	private String email;
	private User.Role role;
}
