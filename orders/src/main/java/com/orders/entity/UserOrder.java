package com.orders.entity;


import java.sql.Time;
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


@Document(collection = "user_order")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder extends BaseEntity{
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "total_items")
	private double totalItems;
	
	@Column(name = "cart_total")
	private double cartTotal;
	
	@Column(name = "items_cost")
	private double itemsCost;
	
	@Column(name = "offer_price")
	private double offerPrice;
	
	@Column(name = "item_total")
	private double itemTotal;
	
	@Column(name = "total_tax")
	private double totalTax;
	
	@Column(name = "total_roundoff")
	private double totalRoundoff;
	
	@Column(name = "address_id")
	private int addressId;
	
	@Column(name = "payment_type")
	private String paymentType;
	
	@Column(name = "order_status")
	private int orderStatus;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "coupon_applied")
	private String couponApplied;
	
	@Column(name = "coupon_percentage")
	private double couponPercentage;
	
	@Column(name = "coupon_amount")
	private double couponAmount;
	
	@Column(name = "coupon_type")
	private String couponType;
	
	@Column(name = "coupon_code")
	private String couponCode;
	
	@Column(name = "delivery_charge")
	private int deliveryCharge;
	
	@Column(name = "slot_date")
	private LocalDate slotDate;
	
	@Column(name = "slot_start_time")
	private Time slotStartTime;
	
	@Column(name = "slot_end_time")
	private Time slotEndTime;
	
	@Column(name = "dated")
	private LocalDate dated;
	
	@Column(name = "app_amount")
	private double appAmount;

}
