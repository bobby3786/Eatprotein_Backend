package com.store.dto;

import lombok.Data;

@Data
public class StoreCategoryDto {
	
	private String id;
	private int categoryId;
	private int storeId;
	private String status;

}
