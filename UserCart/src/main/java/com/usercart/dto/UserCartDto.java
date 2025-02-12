package com.usercart.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class UserCartDto {
	
	private int id;
	private int storeId;
	private int userId;
	private int productId;
	private int priceId;
	private int quantity;
	private LocalDate selectedDate;
	private String selectedSlot;

}
