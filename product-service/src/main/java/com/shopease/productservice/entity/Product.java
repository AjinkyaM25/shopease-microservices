package com.shopease.productservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String name;

	@Column(length = 1000)
	private String description;

	@NotNull(message = "price is required")
	@Positive(message = "Price ust be greater than zero")
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@NotNull(message = "Quantity is required")
	@PositiveOrZero(message = "value is either positive or zero not be in  negative")
	private Integer quantity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@Column(name = "image_url")
	private String ImageURL;

	@Column(name = "Created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public enum Category {
		ELECTRONICS, CLOTHING, FOOD, BOOKS, SPORTS, HOME, BEAUTY, TOYS
	}
	
	
}
