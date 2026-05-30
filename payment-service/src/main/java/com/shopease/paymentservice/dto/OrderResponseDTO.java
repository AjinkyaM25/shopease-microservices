package com.shopease.paymentservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

	private Long id;
	private String orderNumber;
	private Long userId;
	private Long productId;
	private String productName;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	private String status;
	private String deliveryAddress;
	private LocalDateTime orderDate;
	private LocalDateTime updatedAt;
}
