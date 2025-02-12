package com.usercart.entity;

import java.time.LocalDate;
import java.util.Date;

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
@Table(name = "user_cart")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserCart extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "price_id")
	private int priceId;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "selected_date")
	private LocalDate selectedDate;
	
	@Column(name = "selected_slot")
	private String selectedSlot;

}
