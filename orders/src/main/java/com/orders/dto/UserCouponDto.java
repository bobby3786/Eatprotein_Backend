package com.orders.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserCouponDto {
	
	private String id;
	private int userId;
	private int referId;
	private double percentage;
	private int maxLimit;
	private int orderId;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate expireDate;
	private String status;
	private int approvedBy;
	private String notes;

}
