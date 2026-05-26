package com.shopease.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopease.orderservice.dto.ProductResponseDTO;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {
	// Get product by id
	@GetMapping("/api/products/{id}")
	ProductResponseDTO getProductById(@PathVariable Long id);

	// Update Product Quanntity
	@PutMapping("/api/products/{id}/quantity")
	ProductResponseDTO updateQuantity(@PathVariable Long id, @RequestParam Integer quantity);
}
