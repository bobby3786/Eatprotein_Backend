package com.orders.dto;

import lombok.Data;

@Data
public class OrderAddressDto {
	
	private String id;
	private int orderId;
	private String address;
	private String landmark;
	private double latitude;
	private double longitude;

}
