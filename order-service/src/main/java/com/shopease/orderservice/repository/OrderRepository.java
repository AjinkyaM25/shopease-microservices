package com.shopease.orderservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopease.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// Find all orders by user
	List<Order> findByUserId(Long userId);

	// Find By Order Number
	Optional<Order> findByOrderNumber(String orderNumber);

	// Find Orders by Status
	List<Order> findByStatus(Order.OrderStatus status);

	// Find Orders by User and Status
	List<Order> findByUserIdAndStatus(Long userId, Order.OrderStatus status);

	// Check if order Number Exists
	Boolean existsByOrderNumber(String orderNumber);

	// Find Order by Product
	List<Order> findByProductId(Long productId);

}
