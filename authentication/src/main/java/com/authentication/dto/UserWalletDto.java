package com.authentication.dto;

import lombok.Data;

@Data
public class UserWalletDto {
	
	private int id;
	private int userId;
	private String availableTotal;

}
