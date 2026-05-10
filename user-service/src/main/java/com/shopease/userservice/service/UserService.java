package com.shopease.userservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopease.userservice.dto.RegisterRequestDTO;
import com.shopease.userservice.dto.UserResponseDTO;
import com.shopease.userservice.entity.User;
import com.shopease.userservice.exception.UserAlreadyExistException;
import com.shopease.userservice.dto.UserResponseDTO;
import com.shopease.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userrepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserResponseDTO registerUser(RegisterRequestDTO request) {

		if (userrepository.existsByEmail(request.getEmail())) {

			throw new UserAlreadyExistException("User Alredy Exist with this Email: " + request.getEmail());

		}

		// create a New user
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());

		// Encode password before saving - Never save plain password!
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(User.Role.USER);

		// save to database

		User saveUser = userrepository.save(user);

		// Return responseDTO-not the entity
		return mapToResponseDTO(saveUser);
	}

	// Get User By Id
	public UserResponseDTO getUserById(Long id) {

		User user = userrepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with Id :" + id));

		return mapToResponseDTO(user);

	}

	// Get All Users

	public List<UserResponseDTO> getALLUsers() {

		return userrepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());

	}

	// Delete User

	public void deletUser(Long id) {

		if (!userrepository.existsById(id)) {

			throw new RuntimeException("User not found with id :" + id);
		}
		userrepository.deleteById(id);

	}

	// Helper Method - Convert Entity to DTO
	private UserResponseDTO mapToResponseDTO(User user) {

		UserResponseDTO dto = new UserResponseDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		return dto;

	}

}