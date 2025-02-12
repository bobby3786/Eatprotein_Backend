package com.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sharedLibrary.entity.BaseEntity;
@Document(collection = "store_product")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreProduct extends BaseEntity{

	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "category_id")
	private int categoryId;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "avg_rating")
	private double avgRating;
	
	@Column(name = "product_availability")
	private String productAvailability;   //'YES','NO'
	
	@Column(name = "stock_availability")
	private String stockAvailability;     //'YES','NO'
	
	@Column(name = "status")
	private String status;    //'YES','NO'
	
}
