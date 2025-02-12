package com.payments.entity;

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
@Table(name = "payment_fo")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFo extends BaseEntity{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "dated")
	private Date dated;
	
	@Column(name = "fo_id")
	private int foId;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "order_amount")
	private double orderAmount;
	
	@Column(name = "fo_payment")
	private double foPayment;
	
	@Column(name = "total_app_share")
	private double totalAppShare;
	
	@Column(name = "app_payment")
	private double appPayment;
	
	@Column(name = "store_id")
	private int storeId;
	
	@Column(name = "store_payment")
	private double storePayment;
	
	@Column(name = "db_id")
	private int dbId;
	
	@Column(name = "delivery_charges")
	private double deliveryCharges;
	
	@Column(name = "actual_db_charge")
	private double actualDbCharge;
	
	@Column(name = "status")
	private String status;   //'ORDER_PENDING','ORDER_PROCESSED','ORDER_CANCELLED'
	
	@Column(name = "coupon_cost")
	private int couponCost;
	
	@Column(name = "package_charge")
	private int packageCharge;
	
	@Column(name = "platform_fee")
	private int platformFee;
	
	@Column(name = "final_app_amount")
	private double finalAppAmount;
	
	@Column(name = "comments")
	private String comments;

}
