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

@Document(collection = "order_payment")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayment extends BaseEntity{
	
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "pay_mode")
	private String payMode;
	
	@Column(name = "pay_partner")
	private String payPartner;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "ref_id")
	private double refId;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "status")
	private String status;

}
