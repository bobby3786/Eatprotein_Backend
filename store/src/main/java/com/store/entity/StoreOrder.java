package com.store.entity;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "store_order")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StoreOrder extends BaseEntity{
	
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "tax")
	private double tax;
	
	@Column(name = "store_price")
	private double storePrice;
	
	@Column(name = "items_price")
	private double itemsPrice;
	
	@Column(name = "offer_price")
	private double offerPrice;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "accepted_manager_id")
	private int acceptedManagerId;
	
	@Column(name = "assigned_boy_id")
	private int assignedBoyId;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "dated")
	private LocalDate dated;

}
