package com.shopease.productservice.dto;

import java.math.BigDecimal;

import com.shopease.productservice.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

	@NotBlank(message = "Product Name is Required")
	private String name;

	private String discription;

	@NotNull(message = "Price is required")
	@Positive(message = "Value much be greater than zero ")
	private BigDecimal price;

	@NotNull(message = "Quantity is required")
	@PositiveOrZero(message = "Quantity cannot be negative")
	private Integer quantity;

	@NotNull(message = "category is required")
	private Product.Category category;

	private String imageUrl;

}
