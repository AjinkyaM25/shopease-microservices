package com.shopease.userservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.userservice.dto.RegisterRequestDTO;
import com.shopease.userservice.dto.UserResponseDTO;
import com.shopease.userservice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")

public class UserController {

	@Autowired
	private UserService userService;

	// Register New User
	// POST http://localhost:8081/api/users/register //

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO request) {

		UserResponseDTO response = userService.registerUser(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	// Get User by ID
	// Get http://localhost:8081/api/users

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {

		UserResponseDTO response = userService.getUserById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// Get All USers
	// Get http://localhost:8081/api/users
	
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

		List<UserResponseDTO> response = userService.getALLUsers();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	// Delete uSer
	// Delete http://localhost:8081/api/users/1

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {

		userService.deletUser(id);
		return new ResponseEntity<>("User deleted sucessfully", HttpStatus.OK);

	}

}
