package com.catalog.dto;

import lombok.Data;

@Data
public class BrandDto {
	
	private int id;
	private int storeId;
	private String name;
	private String imagePath;
	private String status;

}
