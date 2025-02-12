package com.catalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sharedLibrary.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product_image")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
//	@ManyToOne
//	@JoinColumn(name = "product_id",nullable = false)
//	@JsonIgnoreProperties("products")
	private int productId;
	
	@Column(name = "image_path")
	private String imagePath;

}
