package com.payments.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class PaymentUtilDto {
	
	private int id;
	private int orderId;
	private int storeId;
	private double amount;
	private LocalDate dated;

}
