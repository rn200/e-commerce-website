package com.main.shopapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
public class AdminProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	private String pcategory;
	private long price;
	private int discount;
	private String stock;
	private String upimg;
	private long discountedPrice;
	private Boolean isActive; 
	public AdminProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AdminProduct(int id, String title, String description, String pcategory, long price, int discount,
			String stock, String upimg, long discountedPrice, Boolean isActive) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.pcategory = pcategory;
		this.price = price;
		this.discount = discount;
		this.stock = stock;
		this.upimg = upimg;
		this.discountedPrice = discountedPrice;
		this.isActive = isActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPcategory() {
		return pcategory;
	}
	public void setPcategory(String pcategory) {
		this.pcategory = pcategory;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getUpimg() {
		return upimg;
	}
	public void setUpimg(String upimg) {
		this.upimg = upimg;
	}
	public long getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(long discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
