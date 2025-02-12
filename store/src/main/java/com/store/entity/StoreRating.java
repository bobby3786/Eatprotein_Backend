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
@Document(collection = "store_rating")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreRating extends BaseEntity{
	
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "rating")
	private double rating;
	
	@Column(name = "comments")
	private String comments;

}
