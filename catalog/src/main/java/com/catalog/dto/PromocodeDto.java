package com.catalog.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PromocodeDto {
	
	
	private int id;
	private int storeId;
	private String imagePath;
	private String location;
	private double latitude;
	private double longitude;
	private int radius;
	private String name;
	private String description;
	private String code;
	private String offerPercentage;
	private  String maxLimit;
	private String uniqueUser;
	private String limitApply;
	private int limitCount;
	private int minOrderValue;
	private int numberOfClaims;
	private int usedCount;
	private LocalDate startDate;
	private LocalDate endDate;
	private  String daysLimit;
	private String type;

}
