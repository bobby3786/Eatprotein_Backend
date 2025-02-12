package com.catalog.dto;

import lombok.Data;

@Data
public class ProductTagDto {
	
	private int id;
	private int productId;
	private int tagId;
	private int storeId;

}
