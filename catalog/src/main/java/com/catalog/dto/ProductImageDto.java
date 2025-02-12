package com.catalog.dto;

import lombok.Data;

@Data
public class ProductImageDto {
	
	private int id;
	private int productId;
	private String imagePath;

}
