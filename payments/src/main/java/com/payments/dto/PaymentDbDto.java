package com.payments.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class PaymentDbDto {
	
	private int id;
	private LocalDate dated;
	private int dbId;
	private int storeId;
	private int ordersCount;
	private double orders_amount;
	private double foPayment;
	private double appPayment;
	private double storePayment;
	private double deliveryCharges;
	private String paymentStatus;
	private String comments;
	private String transactionId;
	private LocalDate transactionDate;

}
