package com.catalog.dto;

import java.util.List;

import com.catalog.entity.Product;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SubCategoryDto {
	
    private int id;
	private int categoryId;
	private String name;
	private String imagePath;
	private String status;  //ACTIVE , DISABLED
	private int sortOrder;
	

}
