package com.shopease.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopease.paymentservice.dto.OrderResponseDTO;

@FeignClient(

		name = "order-service", url = "${order.service.url}")

public interface OrderClient {

	// Get Order by Id
	@GetMapping("/api/order/{id}")
	OrderResponseDTO getOrderById(@PathVariable Long id);

	// Update Order Status
	@PutMapping("/api/orders/{id}/status")
	OrderResponseDTO updateOrderStatus(@PathVariable Long id, @RequestParam String status);

}
