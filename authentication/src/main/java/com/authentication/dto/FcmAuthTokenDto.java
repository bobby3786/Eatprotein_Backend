package com.authentication.dto;

import lombok.Data;

@Data
public class FcmAuthTokenDto {
	
	private int id;
	private String token;
	private double expiresIn;

}
