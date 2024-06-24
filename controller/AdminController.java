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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.main.shopapp.helper.Message;
import com.main.shopapp.model.AdminCategory;
import com.main.shopapp.model.AdminProduct;
import com.main.shopapp.model.UserDtls;
import com.main.shopapp.service.CategoryService;
import com.main.shopapp.service.ProductService;
import com.main.shopapp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@ModelAttribute
	public void getAdminDetails(Principal principal,Model model) {
		String email = principal.getName();
		UserDtls userdetails = this.userService.getUserByEmail(email);
	    model.addAttribute("user",userdetails);
	}

	@GetMapping("/")
	public String adminHomePage() {
		return "admin/adminHome";
	}
	
	@GetMapping("/add-products")
	public String addAdminProducts(Model m) {
		m.addAttribute("cat", this.categoryService.getAllCategory());
		return "admin/addproducts";
	}
	
	@GetMapping("/category")
	public String category(Model m) {
		m.addAttribute("categories", this.categoryService.getAllCategory());
		return "admin/category";
	}
	
	@PostMapping("/handleAdminCategory")
	public String handleCategory( @ModelAttribute AdminCategory adminCategory, HttpSession session,@RequestParam("file") MultipartFile file,Model model,Principal principal) throws IOException  {
             
		     model.addAttribute("title", "category page");
		     if(file.isEmpty()) {
				  System.out.println("file is empty");
				  adminCategory.setImagecategory("default.png");
			  }
			  else {
				  adminCategory.setImagecategory(file.getOriginalFilename());
				  
				  File file2 = new ClassPathResource("static/img/").getFile();
				  
				  Path path = Paths.get(file2.getAbsolutePath()+ File.separator+"category_img"+File.separator+ file.getOriginalFilename());
				  System.out.println(path);
				  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			      System.out.println("Image is uploaded");
			  }
			  
			boolean existCategory = this.categoryService.existCategory(adminCategory.getCategory());
			
			if(existCategory) {
				session.setAttribute("message", new Message("Category Already There","danger"));
			}
			else {
				AdminCategory saveCategory = this.categoryService.saveCategory(adminCategory);										
				if(ObjectUtils.isEmpty(saveCategory)) {
					session.setAttribute("message", new Message("Internal Server Error","danger"));
				}
				else {
					
					session.setAttribute("message", new Message("Category Added Successfully","success"));
				}
					
		}
		return "redirect:/admin/category";

	}
	
	@GetMapping("/deletecategory/{id}")
	public String categoryDeletion(@PathVariable("id") int id,HttpSession session) {
		boolean deleteCategory = this.categoryService.deleteCategory(id);
		if(deleteCategory) {
			session.setAttribute("message", new Message("Deletion Successful","success"));
		}
		else {
			session.setAttribute("message", new Message("Something Went Wrong","danger"));
		}
		
		return "redirect:/admin/category";
	}
	
		
	// for updating category
	@GetMapping("/updatecategory/{id}")
	public String categoryUpdate(@PathVariable ("id") int id,Model m) {
		m.addAttribute("category", this.categoryService.getCategoryById(id));
		return "admin/edit";
	}
	
	@PostMapping("/handleUpadteCategory")
	public String handleUpdate(@ModelAttribute AdminCategory adminCategory,@RequestParam("file") MultipartFile file,Model m,HttpSession session) throws IOException {
		
		AdminCategory newCategory = this.categoryService.getCategoryById(adminCategory.getId());
	String imageName=	file.isEmpty() ? newCategory.getImagecategory() : file.getOriginalFilename();
		if(!ObjectUtils.isEmpty(newCategory)) {
			newCategory.setCategory(adminCategory.getCategory());
			newCategory.setIsActive(adminCategory.getIsActive());
			newCategory.setImagecategory(imageName);
			  }
		AdminCategory saveCategory = this.categoryService.saveCategory(newCategory);
		if(!ObjectUtils.isEmpty(saveCategory)) {
			
			if(!file.isEmpty()) {
				File file2 = new ClassPathResource("static/img/").getFile();
				  
				  Path path = Paths.get(file2.getAbsolutePath()+ File.separator+"category_img"+File.separator+ file.getOriginalFilename());
				  System.out.println(path);
				  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			      System.out.println("Image is uploaded");			      			
			}
			
		}
	
			session.setAttribute("message", new Message("Updated successfully","success"));
		return "redirect:/admin/category";
	}

	//admin home page view products field
	
	@GetMapping("/view-products")
	public String viewProducts(Model m) {
		m.addAttribute("products", this.productService.getAllProducts());
		
		return "admin/product_view";
	}
	
	// add-product handle
		@PostMapping("/product-Handle")
		public String handleProduct(@ModelAttribute AdminProduct adminProduct,Model m,@RequestParam("file") MultipartFile file,HttpSession session) throws IOException {
			
			long price = adminProduct.getPrice();
			int discount = adminProduct.getDiscount();
			long nextprice=((100-discount)*price)/100;
			System.out.println(nextprice);
			adminProduct.setDiscountedPrice(nextprice);
			
			if(file.isEmpty()) {
				session.setAttribute("message", new Message("Upload file","danger"));
			    return "redirect:/admin/add-products";
			}
			else {
			  
					adminProduct.setUpimg(file.getOriginalFilename());
					File file2 = new ClassPathResource("static/img/").getFile();
				  
				  Path path = Paths.get(file2.getAbsolutePath()+ File.separator+"product_img"+File.separator+ file.getOriginalFilename());
				  System.out.println(path);
				  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
				  AdminProduct saveProduct = this.productService.saveProduct(adminProduct);
			      if(ObjectUtils.isEmpty(saveProduct)) {
			    	  session.setAttribute("message", new Message("Internal server error!","success"));
			      }
			      else {
			    	  session.setAttribute("message", new Message("Product Added","success"));
			      }
				  
				  System.out.println("Image is uploaded");
			}
			
			return "redirect:/admin/add-products";
		}
	
		
		//product view delete
		
		@GetMapping("/deleteview/{id}")
		public String deleteView(@PathVariable("id") int id,HttpSession session) {
			boolean viewDelete = this.productService.viewDelete(id);
			if(viewDelete) {
			session.setAttribute("message", new Message("Product Deleted","success"));
			}
			else {
				session.setAttribute("message", new Message("Product not Deleted","danger"));
			}
			return "redirect:/admin/view-products";
		}
	
		//update product view page of admin
		@GetMapping("/updatetview/{id}")
		public String upadateView(@PathVariable("id") int id, Model m) {
			m.addAttribute("cat", this.categoryService.getAllCategory());
			m.addAttribute("product", this.productService.getSingleProduct(id));
			return "admin/updateview";
		}
		
		//update product view page
		@PostMapping("/handleupdateproductview")
		public String handleUpdateProductPage(@ModelAttribute AdminProduct adminProduct,HttpSession session,Model m,@RequestParam("file") MultipartFile file) throws IOException {

			AdminProduct newProduct = this.productService.getSingleProduct(adminProduct.getId());
		String imageName=	file.isEmpty() ? newProduct.getUpimg() : file.getOriginalFilename();
		
		int discount = adminProduct.getDiscount();
		long price = adminProduct.getPrice();
		long disprice=((100-discount)*price)/100;
		
		if(!ObjectUtils.isEmpty(newProduct)) {
			    
				newProduct.setTitle(adminProduct.getTitle());
				newProduct.setDescription(adminProduct.getDescription());
				newProduct.setDiscount(discount);
				newProduct.setDiscountedPrice(disprice);
				newProduct.setPcategory(adminProduct.getPcategory());
				newProduct.setPrice(price);
				newProduct.setStock(adminProduct.getStock());
				newProduct.setUpimg(imageName);
				newProduct.setIsActive(adminProduct.getIsActive());
				  }
			AdminProduct saveProduct = this.productService.saveProduct(newProduct);
			if(!ObjectUtils.isEmpty(saveProduct)) {
				
				if(!file.isEmpty()) {
					File file2 = new ClassPathResource("static/img/").getFile();
					  
					  Path path = Paths.get(file2.getAbsolutePath()+ File.separator+"product_img"+File.separator+ file.getOriginalFilename());
					 
					  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				      System.out.println("Image is uploaded");			      			
				}
				
			}
		
				session.setAttribute("message", new Message("Updated successfully","success"));
			return "redirect:/admin/view-products";
		}
		
		//show users details
		@GetMapping("/getusers")
		public String showUsers(Model m) {
			String newRole="ROLE_USER";
			m.addAttribute("users", this.userService.getAllUsers(newRole));
			return "admin/show_users";
		}
		
		//manage user from admin page
		
		@GetMapping("/manageuser/{id}")
		public String manageUser(@PathVariable("id") int id,Model m,HttpSession session) {
			boolean deleteUser = this.userService.deleteUserByAdmin(id);
			if(deleteUser) {
				session.setAttribute("message", new Message("Deleted successfully","success"));
			}
			else {
				session.setAttribute("message", new Message("Something Wrong!","danger"));
			}
			
			return "redirect:/admin/getusers";
		}
		
		//add admin 
		
		@GetMapping("/addadmin")
		public String addAdmin(Model m) {
			String newRole="ROLE_ADMIN";
			m.addAttribute("adminusers", this.userService.getAllUsers(newRole));
			return "admin/add_admin";
			
		}
		
		@PostMapping("/add-addmin")
		public String addAdminHandler(@ModelAttribute UserDtls user,
				HttpSession session,Model m,
				@RequestParam("file") MultipartFile file) throws IOException {
			
			user.setRole("ROLE_ADMIN");
			String encodePassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			user.setCpassword(encodePassword);
			
			if(user.getPassword()!=user.getCpassword()) {
				session.setAttribute("message", new Message("Enter Matching Password!","danger"));
			return "redirect:/admin/addadmin";
			}
			
			String imageName=file.isEmpty()? "default.jpg":file.getOriginalFilename();
			user.setProfilepicture(imageName);
			user.setEmail(user.getEmail().toLowerCase());
			UserDtls saveUser = this.userService.saveUser(user);
			if(!ObjectUtils.isEmpty(saveUser)) {
				
				if(!file.isEmpty()) {
					File file2 = new ClassPathResource("static/img/").getFile();
					  
					  Path path = Paths.get(file2.getAbsolutePath()+ File.separator+"user_img"+File.separator+ file.getOriginalFilename());
					//  System.out.println(path);
					  Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				
				    //  System.out.println("Image is uploaded");
				}
			}
			else {
				session.setAttribute("message", new Message("Something Error!","danger"));
				return "redirect:/admin/addadmin";
			}
			session.setAttribute("message", new Message("User Saved Successfully","success"));
			return "redirect:/admin/addadmin";
			
		}
		
		//admin user
		@GetMapping("/adminuser/{id}")
		public String adminUser(@PathVariable("id") int id,Model m,HttpSession session) {
			boolean deleteUser = this.userService.deleteUserByAdmin(id);
			if(deleteUser) {
				session.setAttribute("message", new Message("Deleted successfully","success"));
			}
			else {
				session.setAttribute("message", new Message("Something Wrong!","danger"));
			}
			
			return "redirect:/admin/addadmin";
		}
		
		//update the admin user
		@GetMapping("/manageupdateuser")
		public String updateUserAccountStatus(@RequestParam Boolean status,@RequestParam Integer id,HttpSession session) {
			
			Boolean updateAccountStatus = userService.updateAccountStatus(id,status);
			if(updateAccountStatus) {
				session.setAttribute("message", new Message("status updated","success"));
			}
			else {
				session.setAttribute("message", new Message("something went wrong!","danger"));
			}
			
			return "redirect:/admin/getusers";
		}
		
}
