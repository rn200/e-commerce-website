package com.main.shopapp.service;

import java.util.List;

import com.main.shopapp.model.AdminCategory;
import com.main.shopapp.model.AdminProduct;

public interface ProductService {

	//to get all products
	public List<AdminProduct> getAllProducts();
	
	// to save products
	public AdminProduct saveProduct(AdminProduct adminProduct);
	
	//delete products
	public boolean viewDelete(int id);
	
	//get single product
	public AdminProduct getSingleProduct(int id);
	
	//search active product
	public List<AdminProduct> getIsActive(String category);
	
}
