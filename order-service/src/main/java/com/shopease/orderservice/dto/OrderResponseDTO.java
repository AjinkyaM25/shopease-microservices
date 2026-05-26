	package com.shopease.orderservice.dto;
	
	import java.math.BigDecimal;
	import java.time.LocalDateTime;
	
	import com.shopease.orderservice.entity.Order;
	
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class OrderResponseDTO {
	
		private Long id;
		private String orderNumber;
		private Long userId;
		private Long productId;
		private String productName;
		private Integer quantity;
		private BigDecimal unitPrice;
		private BigDecimal totalPrice;
		private Order.OrderStatus status;
		private String deliveryAddress;
		private LocalDateTime orderDate;
		private LocalDateTime updatedAt;
	}
