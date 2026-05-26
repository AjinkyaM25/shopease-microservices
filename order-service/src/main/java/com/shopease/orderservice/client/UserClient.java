package com.shopease.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopease.orderservice.dto.UserResponseDTO;

@FeignClient(name = "user-service", url = "${user.service.url}"

)

public interface UserClient {

	// Get User By ID
@GetMapping("/api/users/{id}")
	UserResponseDTO getUserById(@PathVariable Long id);
}
