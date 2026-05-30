package com.shopease.paymentservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")

public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_number", unique = true, nullable = false)
	private String paymentNumber;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Column(name = "order_number", nullable = false)
	private String orderNumber;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", nullable = false)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status", nullable = false)
	private PaymentStatus paymentStatus;

	@Column(name = "transaction_id", unique = true)
	private String transactionId;

	@Column(name = "failure_reason")
	private String failureReason;

	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@Column(name = "update_at")
	private LocalDateTime updateAt;

	public enum PaymentMethod {
		UPI, CREDIT_CARD, DEBIT_CARD, NET_BANKING, WALLET, CASH_ON_DELIVERY

	}

	public enum PaymentStatus {

		PENDING, PROCESSING, SUCCESS, FAILED, REFUNDED, CANCELLED
	}

}
