package com.shopease.paymentservice.repository;

import com.shopease.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Optional<Payment> findByOrderId(Long orderId);

	Optional<Payment> findByOrderNumber(String orderNumber);

	Optional<Payment> findByPaymentNumber(String paymentNumber);

	Optional<Payment> findByTransactionId(String transactionId);

	List<Payment> findByUserId(Long userId);

	List<Payment> findByPaymentStatus(Payment.PaymentStatus paymentStatus);

	List<Payment> findByPaymentMethod(Payment.PaymentMethod paymentMethod);

	Boolean existsByOrderId(Long orderId);

	Boolean existsByTransactionId(String transactionId);
}