package com.shopease.productservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopease.productservice.dto.ProductRequestDTO;
import com.shopease.productservice.dto.ProductResponseDTO;
import com.shopease.productservice.entity.Product;
import com.shopease.productservice.exception.ProductNotFoundException;
import com.shopease.productservice.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	// Add New Product

	public ProductResponseDTO addProduct(ProductRequestDTO request) {

		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDiscription());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setCategory(request.getCategory());
		product.setImageURL(request.getImageUrl());
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());

		Product savedProduct = productRepository.save(product);
		return mapToResponseDTO(savedProduct);

	}

	// Get Product By Id

	public ProductResponseDTO getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		return mapToResponseDTO(product);

	}

	// Get All Products
	public List<ProductResponseDTO> getAllProducts() {
		return productRepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());

	}

	// Update Product
	public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

		product.setName(request.getName());
		product.setDescription(request.getDiscription());
		product.setPrice(request.getPrice());
		product.setQuantity(request.getQuantity());
		product.setCategory(request.getCategory());
		product.setImageURL(request.getImageUrl());
		product.setUpdatedAt(LocalDateTime.now());

		Product updatedProduct = productRepository.save(product);
		return mapToResponseDTO(updatedProduct);

	}

	// Delete product
	public void deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ProductNotFoundException(id);
		}
		productRepository.deleteById(id);
	}

//Search product by Keyword
	public List<ProductResponseDTO> searchProducts(String keyword) {
		return productRepository.searchByKeyword(keyword).stream().map(this::mapToResponseDTO)
				.collect(Collectors.toList());

	}

	// Get products by category
	public List<ProductResponseDTO> getProductsByCategory(Product.Category category) {
		return productRepository.findByCategory(category).stream().map(this::mapToResponseDTO)
				.collect(Collectors.toList());
	}

	// Get products by price range
	public List<ProductResponseDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
		return productRepository.findByPriceBetween(minPrice, maxPrice).stream().map(this::mapToResponseDTO)
				.collect(Collectors.toList());
	}

	// Get products in stock
	public List<ProductResponseDTO> getProductsInStock() {
		return productRepository.findByQuantityGreaterThan(0).stream().map(this::mapToResponseDTO)
				.collect(Collectors.toList());
	}

	// Update quantity (called when order is placed)
	public ProductResponseDTO updateQuantity(Long id, Integer quantity) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

		if (product.getQuantity() < quantity) {
			throw new RuntimeException("Insufficient stock! Available: " + product.getQuantity());
		}
		product.setQuantity(product.getQuantity() - quantity);
		product.setUpdatedAt(LocalDateTime.now());

		Product updatedProduct = productRepository.save(product);
		return mapToResponseDTO(updatedProduct);
	}

	// Helper method
	private ProductResponseDTO mapToResponseDTO(Product product) {
		ProductResponseDTO dto = new ProductResponseDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDiscription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setQuantity(product.getQuantity());
		dto.setCategory(product.getCategory());
		dto.setImageUrl(product.getImageURL());
		dto.setCreatedAt(product.getCreatedAt());
		dto.setUpdatedAt(product.getUpdatedAt());
		dto.setInstock(product.getQuantity() > 0);
		return dto;
	}

}
