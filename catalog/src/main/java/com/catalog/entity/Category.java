package com.catalog.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.sharedLibrary.entity.BaseEntity;

@Entity
@Table(name = "category")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "status")
	private String status;  //YES , NO
	
	@Column(name = "sort_order")
	private int sortOrder;
	
//	@OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonIgnoreProperties("categoryId")
//    private List<SubCategory> subCategories;
//	
//	@OneToMany(mappedBy = "categoryId", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonIgnoreProperties("categoryId")
//	private List<Product> products;

}
