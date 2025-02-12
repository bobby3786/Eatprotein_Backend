package com.authentication.dto;

import lombok.Data;

@Data
public class UserPromocodeDto {
	
	private int id;
	private int userId;
	private String userPhone;
	private int promoId;

}
