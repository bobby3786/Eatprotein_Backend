package com.catalog.dto;

import lombok.Data;

@Data
public class ProductRatingDto {
	
	private int id;
	private int orderId;
	private int storeId;
	private int productId;
	private int priceId;
	private int userId;
	private double rating;
	private String comments;

}
