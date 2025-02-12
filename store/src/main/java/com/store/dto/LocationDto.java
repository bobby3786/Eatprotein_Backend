package com.store.dto;

import lombok.Data;

@Data
public class LocationDto {
	
	private String id;
	private String area;
	private String city;
	private String state;
	private String country;
	private double latitude;
	private double longitude;
	private String status;

}
