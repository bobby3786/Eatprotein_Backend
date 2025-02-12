package com.orders.dto;

import lombok.Data;

@Data
public class OrderLogDto {
	
	private String id;
	private int orderId;
	private int storeId;
	private int status;
	private String statusText;
	private int updatedBy;
	private String notes;

}
