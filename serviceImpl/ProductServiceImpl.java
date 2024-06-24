package com.main.shopapp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.main.shopapp.Repository.ProductRepository;
import com.main.shopapp.model.AdminProduct;
import com.main.shopapp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<AdminProduct> getAllProducts() {
		// TODO Auto-generated method stub
		return this.productRepository.findAll();
		 
	}

	@Override
	public AdminProduct saveProduct(AdminProduct adminProduct) {
		// TODO Auto-generated method stub
		return this.productRepository.save(adminProduct);
	}

	@Override
	public boolean viewDelete(int id) {
		// TODO Auto-generated method stub
		AdminProduct deleteId = this.productRepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(deleteId)) {
			this.productRepository.delete(deleteId);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public AdminProduct getSingleProduct(int id) {
		// TODO Auto-generated method stub
		return  this.productRepository.findById(id).orElse(null);
		
	}

	@Override
	public List<AdminProduct> getIsActive(String category) {
		// TODO Auto-generated method stub
		List<AdminProduct> activeProduct = null;
		if(ObjectUtils.isEmpty(category)) {
			activeProduct=this.productRepository.findByIsActiveTrue();
		}
		else {
			activeProduct=this.productRepository.findByPcategory(category);
		}
		return activeProduct;
	}
	
}
