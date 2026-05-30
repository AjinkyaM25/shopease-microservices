package com.shopease.paymentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.paymentservice.dto.PaymentRequestDTO;
import com.shopease.paymentservice.dto.PaymentResponseDTO;
import com.shopease.paymentservice.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
    private PaymentService paymentService;

    // Process payment
    // POST http://localhost:8084/api/payments
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @Valid @RequestBody PaymentRequestDTO request) {
        PaymentResponseDTO response = 
            paymentService.processpayment(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get payment by id
    // GET http://localhost:8084/api/payments/1
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @PathVariable Long id) {
        PaymentResponseDTO response = 
            paymentService.getPaymentById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Get payment by order id
    // GET http://localhost:8084/api/payments/order/1
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderId(
            @PathVariable Long orderId) {
        PaymentResponseDTO response = 
            paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Get payment by order number
    // GET http://localhost:8084/api/payments/order-number/ORD-123
    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderNumber(
            @PathVariable String orderNumber) {
        PaymentResponseDTO response = 
            paymentService.getPaymentByOrderNumber(orderNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Get all payments by user
    // GET http://localhost:8084/api/payments/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUser(
            @PathVariable Long userId) {
        List<PaymentResponseDTO> response = 
            paymentService.getPaymentsByUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Get all payments
    // GET http://localhost:8084/api/payments
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        List<PaymentResponseDTO> response = 
            paymentService.getAllPayments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Process refund
    // PUT http://localhost:8084/api/payments/1/refund
    @PutMapping("/{id}/refund")
    public ResponseEntity<PaymentResponseDTO> processRefund(
            @PathVariable Long id) {
        PaymentResponseDTO response = 
            paymentService.processRefund(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
	
}
