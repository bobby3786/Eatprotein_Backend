package com.payments.entity;

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
@Table(name = "payment_store")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStore extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "dated")
	private LocalDate dated;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "orders_count")
	private int ordersCount;
	
	@Column(name = "orders_amount")
	private double ordersAmount;
	
	@Column(name = "fo_payment")
	private double foPayment;
	
	@Column(name = "app_payment")
	private double appPayment;
	
	@Column(name = "store_payment")
	private double storePayment;
	
	@Column(name = "delivery_charges")
	private double deliveryCharges;
	
	@Column(name = "payment_status")
	private String paymentStatus;    //'PENDING','PROCESSED','COMPLETED'
	
	@Column(name = "comments")
	private String comments;
	
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name = "transaction_date")
	private LocalDate transactionDate;

}
