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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
@Entity
public class AdminCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;
	
	private String category;
	private String imagecategory;
	private Boolean isActive;
	
}
