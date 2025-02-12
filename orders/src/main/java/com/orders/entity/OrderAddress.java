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
@Document(collection = "order_address")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddress extends BaseEntity{
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "landmark")
	private String landmark;
	
	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = "longitude")
	private double longitude;

}
