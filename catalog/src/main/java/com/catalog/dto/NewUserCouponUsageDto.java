package com.catalog.dto;

import lombok.Data;

@Data
public class NewUserCouponUsageDto {
	
	private int id;
	private int userId;
	private String phone;
	private String couponCode;


}
