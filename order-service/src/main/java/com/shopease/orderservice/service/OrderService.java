package com.shopease.orderservice.service;

import com.shopease.orderservice.client.ProductClient;
import com.shopease.orderservice.client.UserClient;
import com.shopease.orderservice.dto.OrderRequestDTO;
import com.shopease.orderservice.dto.OrderResponseDTO;
import com.shopease.orderservice.dto.ProductResponseDTO;
import com.shopease.orderservice.dto.UserResponseDTO;
import com.shopease.orderservice.entity.Order;
import com.shopease.orderservice.exception.OrderNotFoundException;
import com.shopease.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    // Place new order
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {

        // Step 1 - Verify user exists
        UserResponseDTO user = userClient.getUserById(request.getUserId());
        if(user == null) {
            throw new RuntimeException(
                "User not found with id: " + request.getUserId());
        }

        // Step 2 - Verify product exists
        ProductResponseDTO product =
            productClient.getProductById(request.getProductId());
        if(product == null) {
            throw new RuntimeException(
                "Product not found with id: " + request.getProductId());
        }

        // Step 3 - Check if product is in stock
        if(product.getQuantity() <=0) {
            throw new RuntimeException(
                "Product is out of stock: " + product.getName());
        }

        // Step 4 - Check if enough quantity available
        if(product.getQuantity() < request.getQuantity()) {
            throw new RuntimeException(
                "Insufficient stock! Available: " + product.getQuantity() +
                " Requested: " + request.getQuantity());
        }

        // Step 5 - Calculate total price
        BigDecimal totalPrice = product.getPrice()
            .multiply(BigDecimal.valueOf(request.getQuantity()));

        // Step 6 - Create order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setProductName(product.getName());
        order.setQuantity(request.getQuantity());
        order.setUnitPrice(product.getPrice());
        order.setTotalPrice(totalPrice);
        order.setStatus(Order.OrderStatus.PENDING);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setOrderDate(LocalDateTime.now());
        order.setUpdateAt(LocalDateTime.now());

        // Step 7 - Save order
        Order savedOrder = orderRepository.save(order);

        // Step 8 - Update product quantity
        productClient.updateQuantity(
            request.getProductId(),
            request.getQuantity()
        );

        // Step 9 - Update status to CONFIRMED
        savedOrder.setStatus(Order.OrderStatus.CONFIRMED);
        savedOrder.setUpdateAt(LocalDateTime.now());
        Order confirmedOrder = orderRepository.save(savedOrder);

        return mapToResponseDTO(confirmedOrder);
    }

    // Get order by id
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return mapToResponseDTO(order);
    }

    // Get order by order number
    public OrderResponseDTO getOrderByNumber(String orderNumber) {
        Order order = orderRepository
                .findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(
                    "Order not found with number: " + orderNumber));
        return mapToResponseDTO(order);
    }

    // Get all orders by user
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get all orders
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Update order status
    @Transactional
    public OrderResponseDTO updateOrderStatus(
            Long id, Order.OrderStatus newStatus) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        validateStatusTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);
        order.setUpdateAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(order);
        return mapToResponseDTO(updatedOrder);
    }

    // Cancel order
    @Transactional
    public OrderResponseDTO cancelOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus() == Order.OrderStatus.SHIPPED ||
           order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException(
                "Cannot cancel order that is already " + order.getStatus());
        }

        // Restore product quantity
        productClient.updateQuantity(
            order.getProductId(),
            -order.getQuantity()
        );

        order.setStatus(Order.OrderStatus.CANCELLED);
        order.setUpdateAt(LocalDateTime.now());

        Order cancelledOrder = orderRepository.save(order);
        return mapToResponseDTO(cancelledOrder);
    }

    // Generate unique order number
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" +
               UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Validate status transition
    private void validateStatusTransition(
            Order.OrderStatus current,
            Order.OrderStatus newStatus) {

        if(current == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException(
                "Cannot change status of delivered order!");
        }
        if(current == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException(
                "Cannot change status of cancelled order!");
        }
        if(current == Order.OrderStatus.REFUNDED) {
            throw new RuntimeException(
                "Cannot change status of refunded order!");
        }
    }

    // Helper method
    private OrderResponseDTO mapToResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setUserId(order.getUserId());
        dto.setProductId(order.getProductId());
        dto.setProductName(order.getProductName());
        dto.setQuantity(order.getQuantity());
        dto.setUnitPrice(order.getUnitPrice());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setOrderDate(order.getOrderDate());
        dto.setUpdatedAt(order.getUpdateAt());
        return dto;
    }
}