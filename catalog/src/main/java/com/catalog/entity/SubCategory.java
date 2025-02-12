package com.catalog.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.sharedLibrary.entity.BaseEntity;
@Entity
@Table(name = "sub_category")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties("categoryId,products")
public class SubCategory extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "category_id")
	private int categoryId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "status")
	private String status;  //ACTIVE , DISABLED
	
	
	@Column(name = "sort_order")
	private int sortOrder;
	
//	@ManyToOne
//    @JoinColumn(name = "category_id", nullable = false)
//	@JsonIgnoreProperties("subcategories")
//    private Category categoryId;
//	
//	
//	
//	@OneToMany(mappedBy = "subcategoryId", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonIgnoreProperties("subcategoryId,")
//	@JsonIgnore
//	private List<Product> products;


}
