package com.store.dto;

import lombok.Data;

@Data
public class StoreRatingDto {
	
	private String id;
	private int storeId;
	private int userId;
	private double rating;
	private String comments;

}
