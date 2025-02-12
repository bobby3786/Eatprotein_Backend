package com.orders.dto;

import lombok.Data;

@Data
public class OrderPaymentDto {
	
	private String id;
	private int orderId;
	private String payMode;
	private String payPartner;
	private double amount;
	private double refId;
	private String notes;
	private String status;

}
