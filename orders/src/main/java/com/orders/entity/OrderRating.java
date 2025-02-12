package com.orders.entity;

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

@Document(collection = "order_rating")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderRating extends BaseEntity {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "store_rating")
	private double storeRating;
	
	@Column(name = "db_id")
	private int dbId;
	
	@Column(name = "db_rating")
	private double dbRating;
	
	@Column(name = "comments")
	private String comments;
	

}
