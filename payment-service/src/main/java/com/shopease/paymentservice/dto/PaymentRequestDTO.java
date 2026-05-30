package com.shopease.paymentservice.dto;

import java.math.BigDecimal;

import com.shopease.paymentservice.entity.Payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

	@NotNull(message = "order Id is required")
	private Long orderId;

	@NotNull(message = "order number is required")
	private String orderNumber;

	@NotNull(message = "User id is required")
	private Long userId;

	@NotNull(message = "Amount id required")
	@Positive(message = "Amount must be greater than zero")
	private BigDecimal amount;

	@NotNull(message = "payment method is required")
	private Payment.PaymentMethod paymentMethod;
}
