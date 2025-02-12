package com.authentication.dto;

import lombok.Data;

@Data
public class UserAddressDto {
	
	private int id;
	private int userId;
	private String name;
	private String phone;
	private String flatApartment;
	private String address;
	private String placeId;
	private String pincode;
	private String area;
	private String location;
	private String city;
	private String state;
	private String type;
	private double latitude;
	private double longitude;
	private String status;

}
