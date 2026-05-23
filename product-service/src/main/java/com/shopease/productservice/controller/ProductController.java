package com.shopease.productservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.productservice.dto.ProductRequestDTO;
import com.shopease.productservice.dto.ProductResponseDTO;
import com.shopease.productservice.entity.Product;
import com.shopease.productservice.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")

public class ProductController {

	@Autowired
	private ProductService productService;

	// Add New Product
	// Post http://localhost:8082/api/products
	@PostMapping("/addproduct")
	public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO reuqest) {

		ProductResponseDTO response = productService.addProduct(reuqest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Get all Products
	// Get http://localpost:8082/api/products
	@GetMapping("/getproducts")
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

		List<ProductResponseDTO> response = productService.getAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Get Product by Id
	// Get http://localhost:8082/api/products/1
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {

		ProductResponseDTO response = productService.getProductById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Update Product
	// Put http://localhost:8082/api/products/1
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequestDTO request) {

		ProductResponseDTO response = productService.updateProduct(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	// Delete Product
	// Delete http://localhost:8082/api/produs/1

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	// Search Product by keyword
	// GET http://localhost:8082/api/products/search?keyword=phone
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String keyword) {

		List<ProductResponseDTO> response = productService.searchProducts(keyword);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	// Get all products by category
	// Get http://localhost:8082/api/products/category/ELECTRONICS
	// 200 ok -filtered data returned
	@GetMapping("/category/{category}")
	public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Product.Category category) {

		List<ProductResponseDTO> response = productService.getProductsByCategory(category);

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	// Get Product By Price Range
	// GET http://localhost:8082/api/products/price-range?min=100&max=500
	// 200-ok filtered data returned

	@GetMapping("/price-range")
	public ResponseEntity<List<ProductResponseDTO>> getProductByPriceRange(@RequestParam BigDecimal min,
			@RequestParam BigDecimal max) {
		List<ProductResponseDTO> response = productService.getProductsByPriceRange(min, max);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	// Get Product In stock
	// GET http://localhost:8082/api/products/in-stock
	// 200 OK - in stock products returned
	@GetMapping("/in-stock")
	public ResponseEntity<List<ProductResponseDTO>> getProductsInStock() {
		List<ProductResponseDTO> response = productService.getProductsInStock();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Update product quantity
	// PUT http://localhost:8082/api/products/1/quantity?quantity=5
	// 200 OK - updated product returned
	@PutMapping("/{id}/quantity")
	public ResponseEntity<ProductResponseDTO> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
		ProductResponseDTO response = productService.updateQuantity(id, quantity);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
