package com.shopease.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderRequestDTO {

	@NotNull(message = "User Id is Required")
	private Long userId;

	@NotNull(message = "product Id is required ")
	private Long productId;

	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "quantity must be atleast 1")
	private Integer quantity;

	@NotBlank(message = "Delivery address is required")
	private String deliveryAddress;

}
