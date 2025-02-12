package com.catalog.dto;

import lombok.Data;

@Data
public class CouponsUtilDto {
	

    private int id;
	
	private double referralPercentage;
	
	private double referredPercentage;
	
	private int maxLimit;
	
	private int expireDays;

}
