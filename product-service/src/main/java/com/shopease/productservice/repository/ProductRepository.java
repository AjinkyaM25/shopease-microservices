package com.shopease.productservice.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopease.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// Find by category
	List<Product> findByCategory(Product.Category category);

	// Search by name containing keywords
	List<Product> findByNameContainingIgnoreCase(String keyword);

	// Find by Price Range
	List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

	// Find Products in Stocks
	List<Product> findByQuantityGreaterThan(Integer quantity);

	// Find By category and in stock
	List<Product> findByCategoryAndQuantityGreaterThan(Product.Category category, Integer quantity);

	// Custome JPQL query
	@Query("SELECT p FROM Product p WHERE " + "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Product> searchByKeyword(@Param("keyword") String keyword);

	// Find Cheapest Product
	List<Product> findByOrderByPriceAsc();

	// Find Most Expensive Product
	List<Product> findByOrderByPriceDesc();

}
