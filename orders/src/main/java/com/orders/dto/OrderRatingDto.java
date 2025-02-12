package com.orders.dto;

import lombok.Data;

@Data
public class OrderRatingDto {
	
	private String id;
	private int orderId;
	private int userId;
	private int storeId;
	private double storeRating;
	private int dbId;
	private double dbRating;
	private String comments;

}
