package com.main.shopapp.service;

import java.util.List;

import com.main.shopapp.model.AdminCategory;

public interface CategoryService {

	public AdminCategory saveCategory(AdminCategory adminCategory);
	
	public boolean existCategory(String name);
	
	public List<AdminCategory> getAllCategory();
	
	public boolean deleteCategory(int id);
	
	public AdminCategory getCategoryById(int id);
	
	//search which is active
	public List<AdminCategory> getIsActive();
}
