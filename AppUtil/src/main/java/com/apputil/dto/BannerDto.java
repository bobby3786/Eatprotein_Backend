package com.apputil.dto;

import lombok.Data;

@Data
public class BannerDto {
	
	
    private Integer id;
	
	private String name;
	
	private String description;
	
	private String webUrl;
	
	private String imagePath;
	
	private String type;   //HOME , CATEGORY , LOCATION
	
	private String status;   //ACTIVE , DISABLED
	
	private double latitude;
	
	private double longitude;
	
	private int radius;
	
	private String optionKey;
	
	private String optionValue;

}
