package com.catalog.dto;


import lombok.Data;

@Data
public class ProductDto {
	
     private int id;
	
	private int categoryId;
	
	private int subcategoryId;
	
	private int brandId;
	
	private String productName;
	
	private String description;
	
	private String imagePath;
	
	private String calGrm;
	
	private String calPercent;
	
	private String fatGrm;
	
	private String fatPercent;
	
	private String carbsGrm;
	
	private String carbsPercent;
	
	private String proteinGrm;
	
	private String proteinPercent;
	
	private String summary;

	private int createdBy;
	
	private String status;

}
