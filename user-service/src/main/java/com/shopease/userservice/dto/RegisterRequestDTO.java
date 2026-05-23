package com.shopease.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
	
	@NotBlank(message="Name is required")
	@Size(min=2 , max =50 , message = "Name must be in between 2 to 50 characters")
	private String name;
	
	
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is required")
	private String email;
	
	
	
	@NotBlank(message = "Password is required")
	@Size(min =8 , message ="Password should be minimum 6 character ")
	@Pattern (
			regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
			message = "Password must contain at least" + 
			"one upper case letter , " + 
					"one Number "+
			"one special case characte") 
	private String password;

}
