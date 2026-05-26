package com.shopease.orderservice.controller;

import com.shopease.orderservice.dto.OrderRequestDTO;
import com.shopease.orderservice.dto.OrderResponseDTO;
import com.shopease.orderservice.entity.Order;
import com.shopease.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place new order
    // POST http://localhost:8083/api/orders
    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @Valid @RequestBody OrderRequestDTO request) {
        OrderResponseDTO response = orderService.placeOrder(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get order by id
    // GET http://localhost:8083/api/orders/1
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long id) {
        OrderResponseDTO response = orderService.getOrderById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Get order by order number
    // GET http://localhost:8083/api/orders/number/ORD-123456
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderResponseDTO> getOrderByNumber(
            @PathVariable String orderNumber) {
        OrderResponseDTO response = 
            orderService.getOrderByNumber(orderNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Get all orders by user
    // GET http://localhost:8083/api/orders/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(
            @PathVariable Long userId) {
        List<OrderResponseDTO> response = 
            orderService.getOrdersByUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Get all orders
    // GET http://localhost:8083/api/orders
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> response = orderService.getAllOrders();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Update order status
    // PUT http://localhost:8083/api/orders/1/status?status=SHIPPED
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        OrderResponseDTO response = 
            orderService.updateOrderStatus(id, status);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // Cancel order
    // PUT http://localhost:8083/api/orders/1/cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @PathVariable Long id) {
        OrderResponseDTO response = orderService.cancelOrder(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}