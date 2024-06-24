package com.main.shopapp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.main.shopapp.helper.Message;
import com.main.shopapp.model.UserDtls;
import com.main.shopapp.service.CategoryService;
import com.main.shopapp.service.ProductService;
import com.main.shopapp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@ModelAttribute
	public void getUserDetails(Principal principal,Model model) {
		
		if(principal!=null) {
		String email = principal.getName();
		UserDtls userdetails = this.userService.getUserByEmail(email);
	    model.addAttribute("user",userdetails);
		}
	}
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("category", this.categoryService.getAllCategory());
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/signin")
	public String login() {
		return "login";
	}
	
	@GetMapping("/products")
	public String products(Model model,@RequestParam(value = "findcategory",defaultValue = "") String findcategory) {
		//System.out.println("category="+findcategory);
		model.addAttribute("category", this.categoryService.getIsActive());
		model.addAttribute("products", this.productService.getIsActive(findcategory));
		model.addAttribute("paramvalue", findcategory);
		return "products";
	}
	
	
	@GetMapping("/product-process/{id}")
	public String view_product(Model model,@PathVariable("id") int id) {
	  model.addAttribute("pro",this.productService.getSingleProduct(id));
		return "products_view";
	}
	
	@PostMapping("/saveuser")
	public String saveUserHandler(@ModelAttribute UserDtls user,
			@RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {
		
		user.setRole("ROLE_USER");
		user.setIsEnable(true);
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		user.setCpassword(encodePassword);
		
		if(user.getPassword()!=user.getCpassword()) {
			session.setAttribute("message", new Message("Enter Matching Password!","danger"));
		return "redirect:/register";
		}
		
		String imageName=file.isEmpty()? "default.jpg":file.getOriginalFilename();
		user.setProfilepicture(imageName);
		user.setEmail(user.getEmail().toLowerCase());
		UserDtls saveUser = this.userService.saveUser(user);
		if(!ObjectUtils.isEmpty(saveUser)) {
			
			if(!file.isEmpty()) {
				File file2 = new ClassPathResource("static/img/").getFile();
				  
				  Path path = Paths.get(file2.getAbsolutePath()+ File.separator+"user_img"+File.separator+ file.getOriginalFilename());
				  System.out.println(path);
				  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			      System.out.println("Image is uploaded");
			}
		}
		else {
			session.setAttribute("message", new Message("Something Error!","danger"));
		}
		session.setAttribute("message", new Message("User Saved Successfully","success"));
		return "redirect:/register";
	}
	
}
