package com.orders.dto;

import lombok.Data;

@Data
public class OrderItemDto {

	private String id;
	private int productId;
	private int priceId;
	private String name;
	private int quantity;
	private String size;
	private String measure;
	private double price;
	private double cGst;
	private double sGst;
	private double totalTax;
	private double offerPrice;
	private double itemTotal;
	private int orderId;
	private int storeId;
	private double appShare;
	private double fShare;
	private double storeAmount;

	
}
