package com.orders.entity;

import java.time.LocalDate;

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

@Document(collection = "user_coupon")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserCoupon extends BaseEntity{
	
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "refer_id")
	private int referId;
	
	@Column(name = "percentage")
	private double percentage;
	
	@Column(name = "max_limit")
	private int maxLimit;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "expire_date")
	private LocalDate expireDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "approved_by")
	private int approvedBy;
	
	@Column(name = "notes")
	private String notes;

}
