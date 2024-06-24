package com.main.shopapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.shopapp.model.AdminCategory;

public interface CategoryRepository extends JpaRepository<AdminCategory, Integer> {

public boolean existsByCategory(String category);

public List<AdminCategory> findByIsActiveTrue();
}
