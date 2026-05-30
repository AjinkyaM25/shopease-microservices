package com.shopease.paymentservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopease.paymentservice.client.OrderClient;
import com.shopease.paymentservice.dto.OrderResponseDTO;
import com.shopease.paymentservice.dto.PaymentRequestDTO;
import com.shopease.paymentservice.dto.PaymentResponseDTO;
import com.shopease.paymentservice.entity.Payment;
import com.shopease.paymentservice.exception.PaymentAlreadyExistsException;
import com.shopease.paymentservice.exception.PaymentNotFoundException;
import com.shopease.paymentservice.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderClient orderClient;
	
	//Process Payment
	@Transactional
	public PaymentResponseDTO processpayment(PaymentRequestDTO request) {
		
		//Check Indempotency
		//Payment already exist for this order
		if(paymentRepository.existsByOrderId(request.getOrderId())) {
			
			throw new PaymentAlreadyExistsException("Payment already exist for order:" +request.getOrderNumber());
		}
		
		// step 2 verify order exist
		OrderResponseDTO order = orderClient
				.getOrderById(request.getOrderId());
		if(order == null) {
			
			throw new RuntimeException("Order not found:" + request.getOrderId());
		}
		
		
		// verify order matches order total
		if(request.getAmount().compareTo(order.getTotalPrice()) !=0) {
			throw new RuntimeException("Payment amount " + request.getAmount() + "does not match order total" +order.getTotalPrice());
			
		}
		
		// Create Payment Record
		
		Payment payment = new Payment();
		payment.setPaymentNumber(generatePaymentNumber());
        payment.setOrderId(request.getOrderId());
        payment.setOrderNumber(request.getOrderNumber());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(Payment.PaymentStatus.PROCESSING);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setUpdateAt(LocalDateTime.now());
        
        // Step 5 - Save payment
        Payment savedPayment = paymentRepository.save(payment);

        // Step 6 - Simulate payment processing
        boolean paymentSuccess = simulatePaymentGateway(
            request.getPaymentMethod(),
            request.getAmount()
        );

        // Step 7 - Update payment status based on result
        if(paymentSuccess) {
            savedPayment.setPaymentStatus(
                Payment.PaymentStatus.SUCCESS);
            savedPayment.setTransactionId(generateTransactionId());
            savedPayment.setUpdateAt(LocalDateTime.now());

            // Step 8 - Update order status to CONFIRMED
            orderClient.updateOrderStatus(
                request.getOrderId(), "CONFIRMED");

        } else {
            savedPayment.setPaymentStatus(
                Payment.PaymentStatus.FAILED);
            savedPayment.setFailureReason(
                "Payment declined by bank");
            savedPayment.setUpdateAt(LocalDateTime.now());

            // Step 8 - Update order status to CANCELLED
            orderClient.updateOrderStatus(
                request.getOrderId(), "CANCELLED");
        }

        // Step 9 - Save final payment status
        Payment finalPayment = paymentRepository.save(savedPayment);
        return mapToResponseDTO(finalPayment);
    }

    // Get payment by id
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
        return mapToResponseDTO(payment);
    }

    // Get payment by order id
    public PaymentResponseDTO getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(
                    "Payment not found for order: " + orderId));
        return mapToResponseDTO(payment);
    }

    // Get payment by order number
    public PaymentResponseDTO getPaymentByOrderNumber(
            String orderNumber) {
        Payment payment = paymentRepository
                .findByOrderNumber(orderNumber)
                .orElseThrow(() -> new PaymentNotFoundException(
                    "Payment not found for order: " + orderNumber));
        return mapToResponseDTO(payment);
    }

    // Get all payments by user
    public List<PaymentResponseDTO> getPaymentsByUser(Long userId) {
        return paymentRepository.findById(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get all payments
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Process refund
    @Transactional
    public PaymentResponseDTO processRefund(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> 
                    new PaymentNotFoundException(paymentId));

        // Can only refund successful payments
        if(payment.getPaymentStatus() != 
                Payment.PaymentStatus.SUCCESS) {
            throw new RuntimeException(
                "Cannot refund payment with status: "
                + payment.getPaymentStatus());
        }

        // Update payment status to REFUNDED
        payment.setPaymentStatus(Payment.PaymentStatus.REFUNDED);
        payment.setUpdateAt(LocalDateTime.now());

        // Update order status to REFUNDED
        orderClient.updateOrderStatus(
            payment.getOrderId(), "REFUNDED");

        Payment refundedPayment = paymentRepository.save(payment);
        return mapToResponseDTO(refundedPayment);
    }

    // Simulate payment gateway
    private boolean simulatePaymentGateway(
            Payment.PaymentMethod method,
            java.math.BigDecimal amount) {

        // Cash on delivery always succeeds
        if(method == Payment.PaymentMethod.CASH_ON_DELIVERY) {
            return true;
        }

        // Simulate 90% success rate for other methods
        // In real world this calls Razorpay/Stripe/PayPal API
        double random = Math.random();
        return random > 0.1; // 90% success rate
    }

    // Generate unique payment number
    private String generatePaymentNumber() {
        return "PAY-" + System.currentTimeMillis() + "-" +
               UUID.randomUUID().toString()
                   .substring(0, 8).toUpperCase();
    }

    // Generate unique transaction id
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString()
                            .toUpperCase().replace("-", "");
    }

    // Helper method
    private PaymentResponseDTO mapToResponseDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setPaymentNumber(payment.getPaymentNumber());
        dto.setOrderId(payment.getOrderId());
        dto.setOrderNumber(payment.getOrderNumber());
        dto.setUserId(payment.getUserId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setTransactionalId(payment.getTransactionId());
        dto.setFailureReason(payment.getFailureReason());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setUpdateAt(payment.getUpdateAt());
        return dto;
    }
		
	}
