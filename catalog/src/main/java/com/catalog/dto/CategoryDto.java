package com.catalog.dto;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDto {
	
	private int id;
	private String name;
	private String imagePath;
	private String status;
	private int sortOrder;
	
//	private List<SubCategoryDto> subCategories;

}
