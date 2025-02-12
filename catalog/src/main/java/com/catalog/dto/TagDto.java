package com.catalog.dto;

import lombok.Data;

@Data
public class TagDto {
	
	private int id;
	private String name;
	private String imagePath;
	private String status;
	private String type;
	private int sortOrder;

}
