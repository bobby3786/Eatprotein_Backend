package com.authentication.dto;

import lombok.Data;

@Data
public class UserDto {
	
	
	private int id;
	private String referralcode;
	private String referBy;
	private String referrerCode;
	private int superUserId;
	private double foShare;
	private int radius;
	private int organizationId;
	private String city;
	private String imagePath;
	private String name;
	private String phone;
	private String email;
	private String password;
	private int roleId;
	private String platform;
	private String notificationToken;
	private String osType;
	private String deviceMeta;
	private String status;
	private double dbLatitude;
	private double dbLongitude;
	private String dbStatus;
	private double dbRating;

}
