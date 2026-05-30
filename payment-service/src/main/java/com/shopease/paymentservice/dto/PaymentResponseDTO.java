package com.shopease.paymentservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.shopease.paymentservice.entity.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

	private Long id;
	private String paymentNumber;
	private Long orderId;
	private String orderNumber;
	private Long userId;
	private BigDecimal amount;
	private Payment.PaymentMethod paymentMethod;
	private Payment.PaymentStatus paymentStatus;
	private String transactionalId;
	private String failureReason;
	private LocalDateTime paymentDate;
	private LocalDateTime updateAt;
}
