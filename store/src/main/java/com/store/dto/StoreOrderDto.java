package com.store.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class StoreOrderDto {
	
	private String id;
	private int orderId;
	private int storeId;
	private double tax;
	private double storePrice;
	private double itemsPrice;
	private double offerPrice;
	private double totalPrice;
	private int acceptedManagerId;
	private int assignedBoyId;
	private int status;
	private LocalDate dated;

}
