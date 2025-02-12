package com.store.dto;

import lombok.Data;

@Data
public class StoreProductDto {
	
	private String id;
	private int storeId;
	private int categoryId;
	private int productId;
	private double avgRating;
	private String productAvailability;
	private String stockAvailability;
	private String status;

}
