package com.main.shopapp.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.shopapp.model.AdminProduct;

public interface ProductRepository extends JpaRepository<AdminProduct, Integer> {

	public List<AdminProduct> findByIsActiveTrue();

	public List<AdminProduct> findByPcategory(String category);

	public Optional<AdminProduct> findById(Long productId);
	

}
