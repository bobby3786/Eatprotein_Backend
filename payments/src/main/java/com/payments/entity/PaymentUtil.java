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
@Table(name = "payment_util")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUtil extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "dated")
	private LocalDate dated;

}
