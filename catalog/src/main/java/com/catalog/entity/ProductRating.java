package com.catalog.entity;

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
import com.sharedLibrary.entity.BaseEntity;
@Entity
@Table(name = "product_rating")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRating extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "price_id")
	private int priceId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "rating")
	private double rating;
	
	@Column(name = "comments")
	private String comments;
	

}
