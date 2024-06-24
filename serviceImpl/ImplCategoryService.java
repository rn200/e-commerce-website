package com.main.shopapp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.main.shopapp.Repository.CategoryRepository;
import com.main.shopapp.model.AdminCategory;
import com.main.shopapp.service.CategoryService;
@Service
public class ImplCategoryService implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public AdminCategory saveCategory(AdminCategory adminCategory) {
		// TODO Auto-generated method stub
		return this.categoryRepository.save(adminCategory);
	}

	@Override
	public List<AdminCategory> getAllCategory() {
		// TODO Auto-generated method stub
		return this.categoryRepository.findAll();
	}

	@Override
	public boolean existCategory(String name) {
		// TODO Auto-generated method stub
		return this.categoryRepository.existsByCategory(name);
	}

	@Override
	public boolean deleteCategory(int id) {
		// TODO Auto-generated method stub
		AdminCategory delcat = this.categoryRepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(delcat)) {
			categoryRepository.delete(delcat);
			return true;
		}
				
		return false;
	}

	@Override
	public AdminCategory getCategoryById(int id) {
		// TODO Auto-generated method stub
		AdminCategory catUpdate = this.categoryRepository.findById(id).orElse(null);
				
		return catUpdate;
	}

	@Override
	public List<AdminCategory> getIsActive() {
		// TODO Auto-generated method stub
		List<AdminCategory> activeCategory = this.categoryRepository.findByIsActiveTrue();
		return activeCategory;
	}

}
