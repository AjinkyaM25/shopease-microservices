package com.shopease.productservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.shopease.productservice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
	
private Long id;
private String name;
private String discription;
private BigDecimal price;
private Integer quantity;
private Product.Category category;
private String imageUrl;
private LocalDateTime createdAt;
private LocalDateTime updatedAt;
private boolean instock;

	
	
}
