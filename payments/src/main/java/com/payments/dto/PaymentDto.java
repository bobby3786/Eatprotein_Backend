package com.payments.dto;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class PaymentDto {
	
	private int id;
	private LocalDate dated;
	private int ordersCount;
	private double ordersAmount;
	private double foPayment;
	private double appPayment;
	private double storePayment;
	private double deliveryCharges;
	

}
